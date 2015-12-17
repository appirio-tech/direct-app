<%@page contentType="text/html"
   import="java.net.*,java.util.*,javax.naming.*,java.io.*"
%>
<html>
<head>
   <title>JBoss JNDI Check</title>
   <link rel="stylesheet" href="style_master.css" type="text/css">
   <meta http-equiv="cache-control" content="no-cache">
</head>
<body>
<table width="100%">
   <table>
      <tr>
         <td><img src="images/logo.gif" align="left" border="0" alt="JBoss"></td>
         <td valign="middle">
         <%
         String hostname = "<<Unknown - see log/server.log>>";
         try
         {
            hostname = InetAddress.getLocalHost().getHostName();
         }
         catch(IOException e)
         {
            e.printStackTrace();
         }
         %>
         <h1>JNDI Checking for host <code><%= hostname %></code></h1>
         </td>
      </tr>
   </table>
<hr>
<table border='1'>
   <tr>
      <td><h2>Test</h2></td>
      <td><h2>Result</h2></td>
   </tr>
   <tr>
      <td>InitialContext properites</td>
      <td><table border='1'>
         <%
            try
            {
               InitialContext ctx = new InitialContext();
               Hashtable props = ctx.getEnvironment();
               for (Iterator i = props.keySet().iterator(); i.hasNext();)
               {
                  Object key = i.next();
                  out.print("<tr><td>");
                  out.print(key);
                  out.print('=');
                  out.print(props.get(key));
                  out.print("</td></tr>");
               }
            }
            catch (NamingException e)
            {
               out.print(e.toString());
            }
         %>
      </table></td>
   </tr>
   <tr>
      <td>jndi.properties locations</td>
      <td><table border='1'>
         <%
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            for (Enumeration e = loader.getResources("jndi.properties"); e.hasMoreElements();)
            {
               out.print("<tr><td>");
               out.print(e.nextElement());
               out.print("</td></tr>");
            }
         %>
      </table></td>
   </tr>
   <tr>
      <td>Global bindings</td>
      <td><table border='1'>
         <%
            try
            {
               for (Enumeration e = new InitialContext().listBindings(""); e.hasMoreElements();)
               {
                  out.print("<tr><td>");
                  out.print(e.nextElement());
                  out.print("</td></tr>");
               }
            }
            catch (NamingException ignored) {}
         %>
      </table></td>
   </tr>
   <tr>
      <td>JVM local bindings</td>
      <td><table border='1'>
         <%
            try
            {
               for (Enumeration e = new InitialContext().listBindings("java:"); e.hasMoreElements();)
               {
                  out.print("<tr><td>");
                  out.print(e.nextElement());
                  out.print("</td></tr>");
               }
            }
            catch (NamingException ignored) {}
         %>
      </table></td>
   </tr>
</table>
</body>
</html>
