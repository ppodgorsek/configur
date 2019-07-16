# ConfiguR, a persistent configuration service

ConfiguR is a persistent configuration service, providing modules to store and retrieve configuration properties from various sources.

Those sources can have many types:
* Spring Data repositories,
* flat files,
* others can be easily implemented.

[![Codeship Status for ppodgorsek/configur](https://app.codeship.com/projects/0c73e7d0-77cb-0137-13fa-726db4a00d18/status?branch=master)](https://app.codeship.com/projects/350108)
[![Coverage Status](https://coveralls.io/repos/github/ppodgorsek/configur/badge.svg?branch=master)](https://coveralls.io/github/ppodgorsek/configur?branch=master)

## How does it work?

The following attributes were defined to represent configuration properties:
* a key,
* a value,
* an optional category,
* a name to easily identify properties,
* a description to provide additional information,
* variations for clustered environments according to which node the property is fetched from.

All values are stored as strings in the database but methods have been provided to convert them to various other types after being fetched.

Configuration can be stored in a hierarchical manner in order to provide a better structure:
* categories can be nested,
* properties can simply be defined with no category.

All calls should be made to the `ConfigurationPropertySource` and `ConfigurationService` to make sure mechanisms such as caching are not bypassed.

### Seamless environment integration

If needed, a new property source can be added to the environment upon startup. This can be done by defining a `ConfigurationPropertySourceApplicationListener` bean in the application context. By default, this bean will add to the environment property sources for all `ConfigurationService` instances but you can also define it with the name of the bean which must be used instead.

*Important: as some implementations rely on other beans such as the datasource for Spring Data DAOs, ConfiguR cannot be used to load startup properties such as database configuration. Such properties should remain in flat files or environment properties, ConfiguR can be used for dynamic properties fetched while the application is running.*

### Spring Data JPA

Remember to add the following package to the list scanned by Hibernate to find entity definitions: `com.github.ppodgorsek.configur.springdata.jpa.model`

In case you would like to change the table and column names or the database constraints, simply override the default values by creating a new class in your classpath which will supersede `com.github.ppodgorsek.configur.springdata.jpa.JpaConfigurationMetadata`.

### Cache abstraction

This abstraction allows to cache properties from any `ConfigurationService` instance. To avoid complex invalidation cases, categories are not cached.

In case you would like to change the cache region name, simply override the default value by creating a new class in your classpath which will supersede `com.github.ppodgorsek.configur.cache.CacheConfigurationMetadata`.

## How to use this project

All artefacts of this project are available on Maven’s central repository, which makes it easy to use in your projects.

If you are using Maven, simply declare the following dependencies:
* configur-core:  
`    <dependency>`  
`        <groupId>com.github.ppodgorsek</groupId>`  
`        <artifactId>configur-core</artifactId>`  
`        <version>${configur.version}</version>`  
`    </dependency>`

* configur-cache:  
`    <dependency>`  
`        <groupId>com.github.ppodgorsek</groupId>`  
`        <artifactId>configur-cache</artifactId>`  
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
