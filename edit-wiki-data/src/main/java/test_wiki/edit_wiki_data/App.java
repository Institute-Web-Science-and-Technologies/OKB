package test_wiki.edit_wiki_data;

import java.util.regex.*;

class App
{
  public static void main(String[] args)
  {
    String txt="Q4115189$D321C1EE-26B2-4C6A-B25D-2B3F4D2325A8";

    String re1="(Q)";	// Any Single Character 1
    String re2="(\\d+)";	// Integer Number 1
    String re3="(\\$)";	// Any Single Character 2
    String re4="([A-Z0-9]{8}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{12})";	// SQL GUID 1

    Pattern p = Pattern.compile(re1+re2+re3+re4,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    Matcher m = p.matcher(txt);
    System.out.println(txt.matches(p.pattern()));
    if (m.find())
    {
        String c1=m.group(1);
        String int1=m.group(2);
        String c2=m.group(3);
        String guid1=m.group(4);
        System.out.print("("+c1.toString()+")"+"("+int1.toString()+")"+"("+c2.toString()+")"+"("+guid1.toString()+")"+"\n");
    }
  }
}