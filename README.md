# ScreenShot

We want you to help us build a screenshot as a service. This basically means, we'd like to
see a component/service for inputting as list of URLs and receiving screenshots. This should
be designed as a back-end service that you could run in a datacenter and offer this a
service.

The interface to the main application could be command line interface.

So 
* input: List of URL’s for the component/service to screenshot
* output: A single screenshot of each website.

Easy peasy.


One possible scenario could be that the user enters a list of URLs separated by ";" or a file
with the URLs.
The service should store the result of the request (Oh, how do you store the data?).
The user would then be able to query the service for the results, the user should be able
to retrieve the results at any point in time.
Message queues could be used to separate the different parts of the service and prepare
for scalability.
