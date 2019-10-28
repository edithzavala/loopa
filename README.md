# HAFLoop
(**H**ighly **A**daptive **F**eedback control **Loop**)

### Description:
HAFLoop is a framework for developing adaptive MAPE-K feedback loops for self-adaptive systems. It makes it easy to create full or partially adaptive loops for Java-based applications accelerating the implementation process. HAFLoop framework provides a series of components that implement the generic functionalities of an adaptive loop and an esqueleton that guides the implementation of the application-dependent components.

### The primary goals of HAFLoop are:
- Enable adaptation capabilities to MAPE-K loops
- Reduce code repition through reusability
- Accelarate the implementation of adaptative loops for SASs
- Support adaptive loop with varying (de)centralization level

### Main components of HAFLoop framework (under loopa/loopa/src/main/java/org/loopa/):

![HAFLoop framework] (HAFLoopGitHub.jpg)
- Generic folder: Contains the interfaces and abstract classes that define the structure and behavior of a MAPE-K feedback loop element as well as the components that compose it (element sub-folder). Moreover, contains the structural and behavioral definition of a policy and its manager (documents sub-folder).
- Comm folder: Contains de definition of a message. Messages are used for sharing information among the components of a MAPE-K feedback loop element.
- Element folder: Contains the extensions and instantiations of the components of a MAPE-K feedback loop element, as well as their internal modules (see figure below for details). 
- Monitor, Anlyzer, Planner, Executer and Knowledge base folders: Contains the instantiations of the MAPE-K feedback loop elements, respectively. 
- Autonomic manager folder: Contains an instantiation of a complete adaptive MAPE-K feedback loop.

### System requirements:
- Java 1.8 or greater
- Gradle 4.5 or greater

### Getting started:
1) Clone this repository
2) Add the HAFLoop framework to your project


This implementation of LoopA is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

**Main contact:** Edith Zavala (<zavala@essi.upc.edu>)
