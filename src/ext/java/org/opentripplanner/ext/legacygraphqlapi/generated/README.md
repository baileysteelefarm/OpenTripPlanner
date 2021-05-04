# Autogenerated code for legacy GraphQL

## Requirements

- NodeJS (version 10 or newer)

## Running

***NOTE, there are some errors in the generated LegacyGraphQLTypes.java that need to be manually fixed related to use of enums***

The files can be generated using the following snippet
```
yarn install
yarn generate
```

***TODO fix this as it currently does not run and uses wrong version of @graphql-codegen/java***
```
npx -p @graphql-codegen/add -p @graphql-codegen/cli -p @graphql-codegen/java -p @graphql-codegen/java-resolvers -p graphql graphql-codegen -c graphql-codegen.yml 
```

## Changelog

### 2.1.0
- Added ids parameter to bikeRentalStations query (https://github.com/opentripplanner/OpenTripPlanner/pull/3450)
- Added capacity and allowOverloading fields to bike rental stations (not yet properly implemented) (https://github.com/opentripplanner/OpenTripPlanner/pull/3450)
- Updated documentation and process for generating Java code from GraphQL schema definition (https://github.com/opentripplanner/OpenTripPlanner/pull/3450)