# Http-integration-double or HID

This is much a work in progress and also a pet project.

So it's much more about learning, so you should probably use stubby4j instead.

Why this is learning

* Pure JDK, no dependencies except for TestNG
* TestNG instead of jUnit
* Trying to get the hang of TDD
* Using sonarqube

Implementations are done pretty badly, since I want to refactor later one of the simpler things done when practicing TDD
so it's an experiment if it's actually is simpler and also if TDD ends in ATDD. 

There's also a todo list which includes jmh, jcstress, jol.

## Usage
And if you dare to use it

The following thingis exists:

#### Matchers
* And
* Or
* AlwaysTrue
* AlwaysFalse
* Header
* RegExp
* URI
* AttributeQName
* ElementQName
* XPath (namespace and attribute by ending with /@attributeName match first instance and /text() means it must have text)

#### Extractors
* XPath (namespace and attribute by ending with /@attributeName will extract last match and /text() makes sure it has text)
* RegEx (will only extract named groups and they will replace [groupName]_[n]
* Defaults to NOOP

#### Response
* StaticResource
* TokenReplacer
* HalfResponse

### Example and then description

    HIDServer hidServer = hid(
                givenContext("/echo")
                        .whenRequestMatches(and(httpMethodMatcher("POST"), elementQNameMatcher("","message")))
                        .thenExtract(xPathExtractor().xpath("/request/message","message"))
                        .thenRespondWith(tokenReplacer(TestUtil.getResourceStream("/responses/EchoResponse.xml")))
                        .delayStatusFor(0)
                        .respondWithStatusCode(200)
                        .delayResponseBodyFor(0)
                        .writeBodyFor(0)
                        .shouldClose(true)
        )
                .firstPort(1234)
                .lastPort(1255)
                .start();

HIDServer is the server control unit aka, .start(), .stop() and the .createURL(path)  
Since you might want to test a lot of stuff in parallel you define a port range, which is the reason for the createURL.
It creates a URL for that specific server and you don't need to bother with which port it started on.
createURL doesn't match the path, just checks that the path starts with / and that the httpServer is started.

hid() is a static method in npetzall.hid.gwt.HIDGWT so you static import hid and also givenContext.  

givenContext(String contextPath) sets the path after host:port  
whenRequestMatches() takes one HIDMatcher so use "and" or "or" matcher for combinations.  
thenExtract() takes a HIDExtractor if omitted defaults to NOOPExtractor.  
thenRespondWith() takes a HIDResponse there are two available Static and TokenReplacer.  
delayStatusFor() defaults to 0 but is milliseconds from when the theRespondWith has been 
called before it sends the responseHeaders and status. It's a best effort, it might delay 
but might not if the request has in general a long latency.  
respondWithStatusCode() defaults to 200, use it you want to change.  
delayResponseBodyFor() defaults to 0, time before starting to sending responsebody.  
writeBodyFor() defautls to 0, time in milliseconds for sending the response.  
shouldClose() defaults to true, I think that it will always close if full response according to header (Content-Length) has been sent.

hid().firstPort() defaults to 1234, start of port range.
hid().lastPort() defaults to 1342, end inclusive port range.

hid().start() starts the http server.
