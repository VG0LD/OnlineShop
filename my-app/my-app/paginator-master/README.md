# Component Factory Paginator for Vaadin 10+

This is server-side component of [&lt;vcf-paginator&gt;](https://github.com/vaadin-component-factory/vcf-paginator) Web Component.
It displays full functioning paginator on a webpage.

[Live Demo ↗](https://incubator.app.fi/paginator-demo/paginator)

[<img src="https://raw.githubusercontent.com/vaadin/incubator-paginator/master/screenshot.png" width="400" alt="Screenshot of incubator-paginator">](https://vaadin.com/directory/component/vaadinincubator-paginator)


## Usage

A simple use of the paginator component would be the following: create paginator, set total pages number, current page number 
and optionally, text for first and last page buttons. Add paginator to page and add listener for change of selected page to paginator.
```
// 10 pages, current page 1
Paginator paginator = new Paginator(15, 1, "First", "Last");

paginator.addChangeSelectedPageListener(event -> {
    h3.setText("Page: " + event.getPage());
});
```

### With a grid
The content of the grid can be displayed in a paginated way using the paginator.
This requires some basic configuration of the grid and the paginator.

```java
Grid<Person> grid = new Grid<>();

grid.addColumn(Person::firstName);
grid.addColumn(Person::lastName);

List<Person> people = generatePeople(100);

Paginator gridPaginator = new Paginator();

int numberItems = people.size();
int itemsPerPage = 10;
int numberPages = numberItems / itemsPerPage;

gridPaginator.setNumberOfPages(numberPages);

gridPaginator.addChangeSelectedPageListener(event -> {
    loadItems(people,grid,event.getPage(),itemsPerPage);
});

loadItems(people,grid,1,itemsPerPage);
add(grid,gridPaginator);
```


## Setting up for development:

Clone the project in GitHub (or fork it if you plan on contributing)

```
git clone git@github.com/vaadin-component-factory/paginator.git
```

To build and install the project into the local repository run 

```mvn install -DskipITs```

in the root directory. `-DskipITs` will skip the integration tests, which require a TestBench license. If you want to run all tests as part of the build, run

```mvn install```

## Demo

The Demo can be run going to the project paginator-demo and executing the maven goal:

```mvn jetty:run```


## License & Author

This Add-on is distributed under Apache 2.0

Component Factory Paginator is written by Vaadin Ltd.

### Sponsored development
Major pieces of development of this add-on has been sponsored by multiple customers of Vaadin. Read more  about Expert on Demand at: [Support](https://vaadin.com/support) and  [Pricing](https://vaadin.com/pricing)
