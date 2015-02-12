# YAML for Graphs = Graml
[![License](http://img.shields.io/badge/license-APACHE-blue.svg?style=flat)](http://choosealicense.com/licenses/apache-2.0/)
[![License](http://img.shields.io/badge/semver-2.0.0-blue.svg?style=flat)](http://semver.org/spec/v2.0.0)
[![Download](https://api.bintray.com/packages/segfly/maven/graml/images/download.svg)](https://bintray.com/segfly/maven/graml/_latestVersion)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.segfly.graml/graml/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.segfly.graml/graml)
[![Build Status](https://travis-ci.org/segfly/graml.svg)](https://travis-ci.org/segfly/graml)
[![Coverage Status](https://coveralls.io/repos/segfly/graml/badge.svg?branch=master)](https://coveralls.io/r/segfly/graml?branch=master)

<img src="http://graml.segfly.com/images/graml-logo.png" alt="Graml Logo" width="120" align="left">

### About
Graml is a lightweight graph mark-up language based on [YAML](http://en.wikipedia.org/wiki/YAML).
It is designed to represent human-readable graphs more compactly than alternatives such as JSON (GraphSON) or XML (GraphML).

#### Features:
* YAML-based graph representations
* Java reference implementation
* [Tinkerpop Blueprints](http://blueprints.tinkerpop.com/) support for [OrientDB](https://github.com/orientechnologies/orientdb)
[Neo4j](https://github.com/neo4j/neo4j)
[MongoDB](https://github.com/mongodb/mongo) and more!

#### Roadmap:
* Customizable section names
* Graph serialization to Graml
* DB class creation
* Stream processing

## Usage
To enable Graml in your project, simply declare the dependency:

Gradle:

```groovy
compile 'com.segfly.graml:graml:1.0.0'
```

Maven:

```xml
<dependency>
  <groupId>com.segfly.graml</groupId>
  <artifactId>graml</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Quickstart

Graml requires a specific data structure and can not read arbitrary YAML.
Given a Graml-compliant file, getting started is simple:

```java
TinkerGraph g = new TinkerGraph();
GramlReader graml = new GramlReader(g);
graml.load(new File ("/path/to/file.yaml"));
```

Using an empty in-memory [TinkerGraph](https://github.com/tinkerpop/blueprints/wiki/TinkerGraph) we construct a `GramlReader`.
We then can load any number of Graml-formatted YAML representations.

### Example Graml
Graml has an intuitive structure and syntax.
YAML's inline lists (via [ ] ) and associative-lists (via { } ) allow simple one to many relationships on a single line.

```yaml
graml: {version: 1.0}

graph:
  apple: {growsOn: tree}
  tree: {dependsOn: water, exhale: O2}
  water: {precipitatesAs: [rain, snow, sleet, hail]}
```

## Graml Basics
Graml contains five main sections:

Section   | Required | Purpose
----------|----------|--------
header    | Yes      | Metadata
classmap  | No       | Object-oriented DB support
graph     | Yes      | Graph relationships
vertices  | No       | Vertex properties
edges     | No       | Edge properties

### Header
The _required_ header must be present:
```yaml
graml: {version: 1.0}
```

### Classmap
The _optional_ classmap section supports object-oriented databases such as OrientDB.
When resolving a vertex or edge, an entity's class is determined by this section.

```yaml
classmap:
  fruit: [apples, oranges, pears]
  plant: tree
  verb: growsOn
```

Another way to think of this is in terms of how it affects names of entities sent to the Blueprints API. Consider the following graph with the above classmap example:
 
```yaml
graph:
  apple: {growsOn: tree}
```

This will result in two vertices with the names "fruit:apples" and "plant:tree" and an edge "verb:growOn".
An underlying implementation like OrientDB will use the prefix to map the entity to a class within the database.
The Graml reference implementation will not create the classes.
Future versions may address this shortcoming. 

### Graph Definition
The _required_ graph section captures relationships between vertices.
The most simple expression is a nested associative array representing vertex-edge-vertex:

```yaml
graph:
   parent: {edge: child}
```

This will create verticies named "parent" and "child" connected with an edge named "edge".
Whether this is a bidirectional or unidirectional relationship is determine by the underlying graph database.

A more complex example:

```yaml
graph:
  apple: {growsOn: tree}
  tree: {dependsOn: water, exhale: O2}
  water: {precipitatesAs: [rain, snow, sleet, hail]}
```

### Entity Properties
The _optional_ entity sections allow the assignment of properties and values to vertices and edges in the graph section.

```yaml
vertices:
  apple:
    color: red
    weight: 200g 
    ripe: yes

edges:
  dependsOn:
    required: true
```

## Limitations
Graml currently has the following limitations:

#### 1. Vertices must be unique
Graml does not support multiple vertices with the same name in accordance with the Yaml specification.

```yaml
graph:
  apple: {growsOn: tree}
  apple: {influencedBy: gravity}
```

The above will result in an apple vertex with one edge "gravity".
Future versions of Graml will likely detect this and throw an exception.

If multiple instances of an apple are desired, they should be created with specific names:

```yaml
classmap:
  apple: [myApple, yourApple]
  
graph:
  myApple: {grownIn: Washington}
  yourApple: {grownIn: Japan}
```

The example above uses an apple class to characterize the two apples as a single type.
If your graph database implementation does not support classes, the same could be done with a "class" relationship.

#### 2. Edge properties are assigned globally

Multiple edges with the same name may be defined throughout the graph.
However, edge properties are assigned globally:

```yaml
edges:
  grownIn:
    date: October
```

Carrying forward with the myApple/yourApple example,
the above edge property would have been applied to the "grownIn" edge for both apples.

This may be addressed in the future with edge IDs.

#### 3. Huge Graphs

Graml's design prioritizes ease-of-use over speed and scale.
If you have huge graphs, Graml may not be for you.
At least not yet.

A future version may incorporate some degree of stream-processing to better handle huge graphs.
The current implementation expects to process the entire graml file at once which places memory limits on the size of graph. 

## Getting Involved

Contributions welcome!
If you want to find out how you can get involved, please read the contributing [guidelines](CONTRIBUTING.md).
