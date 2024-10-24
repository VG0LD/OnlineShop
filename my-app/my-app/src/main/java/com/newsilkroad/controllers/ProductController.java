package com.newsilkroad.controllers;

import com.newsilkroad.data.CategoryRepository;
import com.newsilkroad.data.ProductRepository;
import com.newsilkroad.data.SubCategoryRepository;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping(value = "/api")
public class ProductController {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	SubCategoryRepository subCategoryRepository;
	@Autowired
	ProductRepository productRepository;


	@ResponseBody
	@RequestMapping(value = "/start_cat", method = RequestMethod.GET)
	public Object start_cat() throws IOException {
		String path = "C:\\desc";

		String Category = "Category.csv";
		String SubCategory = "SubCategory.csv";


		File file = new File(path + File.separator + Category);
		int i = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;

			while ((line = reader.readLine()) != null) {
				if (i > 0) {
					com.newsilkroad.data.Category category = new com.newsilkroad.data.Category();
					String[] str = line.split(";");
					category.setName(str[1]);
					try {
						category.setDescription(str[2]);
					} catch (Exception e) {
						e.printStackTrace();
					}
					categoryRepository.save(category);
				}
				i++;
			}
			i = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		file = new File(path + File.separator + SubCategory);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (i > 0) {
					com.newsilkroad.data.SubCategory subCategory = new com.newsilkroad.data.SubCategory();
					String[] str = line.split(";");
					subCategory.setName(str[1]);
					try {
						subCategory.setDescription(str[2]);
					} catch (Exception e) {
						e.printStackTrace();
					}
					subCategory.setCategoryId(Integer.parseInt(str[3]));
					subCategoryRepository.save(subCategory);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/start_prod", method = RequestMethod.GET)
	public Object start_prod() throws IOException, CsvValidationException {
		String path = "C:\\desc";

		String Alibaba_Products = "Alibaba_Products.csv";
		String CPU = "CPU.csv";
		String RAM = "RAM.csv";
		String Motherboard = "Motherboard.csv";

		save(path, Alibaba_Products);
		save(path,CPU);
		save(path,RAM);
		save(path,Motherboard);

		return "success";
	}

	private void save(String path, String fileName) throws CsvValidationException {
		int k = 0;
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(path + File.separator + fileName))
				.withCSVParser(new CSVParserBuilder()
						.withSeparator(';')
						.build())
				.build()) {

			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				if(k>0){
					int i=0;
					com.newsilkroad.data.Product product = new com.newsilkroad.data.Product();
					for (String cell : nextLine) {
						if (i == 1) {
							product.setName(cell);
						}else if(i == 2){
							try {
								product.setPrice(Float.parseFloat(cell));
							} catch (Exception e) {}
						}else if (i == 3){
							try {
								product.setCurrencyCode(cell);
							} catch (Exception e) {}
						}else if(i == 4){
							try {
								if (cell.length() > 255) {
									product.setDescription(cell.substring(0, 255));
								} else {
									product.setDescription(cell);
								}
							} catch (Exception e) {}
						}else if(i == 5){
							try {
								product.setPicture_fileName(cell);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(i == 6){
							try {
								product.setSubCategoryId(Integer.parseInt(cell));
							} catch (Exception e) {
							}
						}
						i++;
					}
					productRepository.save(product);
				}
				k++;
			}
		}catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}


	@ResponseBody
	@RequestMapping(value = "/img", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getPhoto(@RequestParam("img") String img) {
		try {
			String rootPath = "C:\\desc" + File.separator + "product" + File.separator + img;
			InputStream in = new FileInputStream(new File(rootPath));
			byte[] imageBytes = IOUtils.toByteArray(in);

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}













	}

























