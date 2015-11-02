pac4j-underarmour
============================

Pac4J integration for the Under Armour (MapMyRun / MapMyRide) API.


### Installation

Bring in the library by adding the following to your ```build.sbt```. 

  - The release repository: 

```
   resolvers ++= Seq(
     "Millhouse Bintray"  at "http://dl.bintray.com/themillhousegroup/maven"
   )
```
  - The dependency itself: 

```
   libraryDependencies ++= Seq(
     "com.themillhousegroup" %% "pac4j-underarmour" % "0.1.9"
   )

```

Please note - this library is not ready for production use! Once it is, the version number will be in the `1.x` range.


### Usage

Once you have __pac4j-underarmour__ added to your project, you can start using it like this:

```
import com.themillhousegroup.pac4j-underarmour


```

### Branding 
Note that according to the [Developer Guidelines](https://developer.underarmour.com/docs/v70_Authentication) you are __required__ to use the following buttons for login buttons:

![b1](http://developer-ua.mapmyfitness.com.s3.amazonaws.com/assets/login_buttons/UA-login_btn-xlarge.png)
![b2](http://developer-ua.mapmyfitness.com.s3.amazonaws.com/assets/login_buttons/UA-login_btn-large.png)
![b3](http://developer-ua.mapmyfitness.com.s3.amazonaws.com/assets/login_buttons/UA-login_btn-medium.png)

Don't forget! 


### Still To-Do

### Credits

- [Pac4j](https://github.com/pac4j/pac4j)
- [Under Armour Developer documentation](https://developer.underarmour.com/docs)

