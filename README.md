# Servlet_WEB_API
1. I guess there`s no reasons write here how to deploy .war file :)
2. Use the path: ["your domain + port" + /ServletWebGL/?q=Java&limit=1000&length=5&includeMetaData=true]
3. You can change <limit> parameter, and you will see it returns requested number of chars, 
   with a word you inputed in <q> parameter. 
   Example:
	(if you input q=Java&limit=8) it will return - "text":["Java", "Java"],
	(if you input q=Java&limit=6) it will return - "text":["Java"].
4. As you change <length> parameter, you will see it returns a string with length you inputed. 
   But the most interesting thing here, 
   when you input <?q=java&limit=14&length=10> it will return - "text":["java", "13ijfjava"],
   but if your request will be like <?q=java&limit=14&length=4>, the response - "text":["java", "java"],
   so it skips "13ijfjava"(coz it`s length > requested length), and it`s looking for the next word with smaller length.
5. File with text is in "files" folder.
6. I attached a javadoc also.
7. I created some little Rest class instead of test.
   You will find JsonChecking class and appropriate Json library in "lib" folder.
   Short story, how does it works: 
	1. You have a String there, which is a URL.
	2. The program send "GET" request, and receive data.
	3. You can get all data from response, using methods of JSONObject and JSONArray classes.
	4. So that`s my way to check my app.
8. And you can check how it works under the pressure, I mean use 3-4 browsers, and you will see it`s working good,
   try to send a lot of request. 
   I used LOIC program to immitate a "ddos attacks", it sent a 1000+ request and program worked well, just was a little slower.
   I used ConcurrentHashMap and CopyOnWriteArrayList to reach that. 

 
