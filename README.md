pac4j-underarmour
============================

[Pac4j](https://github.com/pac4j/pac4j) integration for the Under Armour (MapMyRun / MapMyRide) API.


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
     "com.themillhousegroup" %% "pac4j-underarmour" % "0.1.32"
   )

```

Please note - this library is not ready for production use! Once it is, the version number will be in the `1.x` range.


### Usage

Once you have __pac4j-underarmour__ added to your project, you can start using it like this:

##### Add it to your list of clients in your setup code:
```
import com.themillhousegroup.pac4j-underarmour

...
val facebookClient = new FacebookClient("fbId", "fbSecret")
...
val underarmourClient = new UnderArmourClient("uaId", "uaSecret")
...

new Clients(baseUrl + "/callback", facebookClient, underarmourClient)

```

### Important Note about callback URLs
The UA OAuth API seems particularly fussy and buggy in its handling of callback URLs.
In particular, it will not handle the standard pac4j callback format, which looks like this:

```
http://www.myapp.com/callback?client_name=UnderArmourClient
```

If you try and do this, the `client_name` parameter will get swallowed up inside the UA code, and your callback will instead be invoked like this:

```
http://www.myapp.com/callback?state=&code=abc71d095717827c79c7753f42e324f8119f123e
```
which is no good at all because pac4j needs that `client_name` to work out which client to dispatch to for completion of the authorization process.

To work around this, *pac4j-underarmour* rewrites the callback URL to this format:
```
http://www.myapp.com/UnderArmourClient/callback
```

#### What this means for you:
- When registering a callback URL for your app, you need to do so in the above format, or you'll see the following error:
```
The requested redirect didn't match the client settings.
```
- YOU (as the web application developer) will need to implement a redirect from `/UnderArmourClient/callback` to `/callback?client_name=UnderArmourClient` to get the pac4j handling to work properly.

##### Here's an example of how you would do it in Play Framework 2.4 (Scala):

`conf/routes`:

```
GET         /UnderArmourClient/callback     controllers.MyController.redirectUA

```

`app/controllers/MyController.scala`:

```
package controllers

import play.api.mvc._

class MyController extends Controller {
	def redirectUA = Action { request =>
		val rawQS = request.rawQueryString
		val target = s"/callback?${rawQS}&client_name=UnderArmourClient"
		Redirect(target)
	}
}
```

#### If you'd like to use a different form of callback URL:

The `UnderArmourClient` constructor accepts an optional third `String` parameter which, if supplied, will be used to form the final callback URL passed over to UnderArmour. 

##### Example:

Specify a custom callback URL in the constructor:
```
  val underArmourClient = new UnderArmourClient(uaId, uaSecret, "/mySpecial/callbackUrl")
```
 
After granting authority, the UnderArmour servers will then make a `GET` request to:
```
  http://my-base-address/mySpecial/callbackUrl?state=&code=abcd1234
```
And you will need to ensure that a handler is present to then redirect to the final endpoint:
```
  http://my-base-address/callback?state=&code=abcd1234&client_name=UnderArmourClient
```
### Branding 
Note that according to the [Developer Guidelines](https://developer.underarmour.com/docs/v70_Authentication) you are __required__ to use the following buttons for login buttons:

![b1](http://developer-ua.mapmyfitness.com.s3.amazonaws.com/assets/login_buttons/UA-login_btn-xlarge.png)
![b2](http://developer-ua.mapmyfitness.com.s3.amazonaws.com/assets/login_buttons/UA-login_btn-large.png)
![b3](http://developer-ua.mapmyfitness.com.s3.amazonaws.com/assets/login_buttons/UA-login_btn-medium.png)

Don't forget! 

### Credits

- [Pac4j](https://github.com/pac4j/pac4j)
- [Under Armour Developer documentation](https://developer.underarmour.com/docs)

