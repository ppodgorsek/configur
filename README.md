# ConfiguR, a persistent configuration service

ConfiguR is a persistent configuration service, providing modules to store and retrieve configuration properties from various sources.

Those sources can have many types:
* Spring Data repositories,
* others can be easily implemented.

## How does it work?

The following attributes were defined to represent configuration properties:
* a key,
* a value,
* an optional category,
* a name to easily identify properties,
* a description to provide additional information.

All values are stored as strings in the database but methods have been provided to convert them to various other types after being fetched.

Configuration can be stored in a hierarchical manner in order to provide a better structure:
* categories can be nested,
* properties can simply be defined with no category.

All calls should be made to the configuration service to make sure mechanisms such as caching are not bypassed.

## How to use this project

All artefacts of this project are available on Maven’s central repository, which makes it easy to use in your projects.

If you are using Maven, simply declare the following dependencies:
* configur-core:  
`    <dependency>`  
`        <groupId>com.github.ppodgorsek</groupId>`  
`        <artifactId>configur-core</artifactId>`  
`        <version>${configur.version}</version>`  
`    </dependency>`

* configur-springdata-jpa:  
`    <dependency>`  
`        <groupId>com.github.ppodgorsek</groupId>`  
`        <artifactId>configur-springdata-jpa</artifactId>`  
`        <version>${configur.version}</version>`  
`    </dependency>`

`configur-core` is the only mandatory one, the others are optional and depend on what persistent layers your project relies on.

## Please contribute!

Have you found an issue? Do you have an idea for an improvement? Feel free to contribute by submitting it [on the GitHub project](https://github.com/ppodgorsek/configur/issues).
