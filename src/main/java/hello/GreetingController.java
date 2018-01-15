package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class GreetingController {

    private static final String filePath = "Z:\\";
    private static final String pattern = "^10.11.1000.*";
    private static final String last_build_num_pattern = "[1-9]+.*$";
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping(value = "/getBuilds", method = RequestMethod.GET)
    public Builds getBuilds(){
        List list = getFileSort(filePath);
        return new Builds(list);
    }

    public static List getBuildsStringListFromFileSys(String path){
        File f = new File(path);
        List l = new ArrayList<String>();
        File[] fl = f.listFiles();
        for(int i=0; i<fl.length; i++)
        {
            if(fl[i].isDirectory()){
                String tempStr = fl[i].getName();
                boolean isMatch = Pattern.matches(pattern, tempStr);
                if(isMatch)
                {
                    l.add(fl[i].getName());
                }
            }
        }
        return l;
    }

    public static List<String> getFileSort(String path) {

        List<String> list = getBuildsStringListFromFileSys(path);
        if (list != null && list.size() > 0) {

            Collections.sort(list, new Comparator<String>() {
                public int compare(String file, String newFile) {
                    String fStr1 = file.split("\\.")[3];
                    String fStr2 = newFile.split("\\.")[3];
                    String temp1 = "";
                    String temp2 = "";
                    Pattern pattern = Pattern.compile(last_build_num_pattern);
                    Matcher m1 = pattern.matcher(fStr1);
                    Matcher m2 = pattern.matcher(fStr2);
                    if(m1.find()) temp1 = m1.group();
                    if(m2.find()) temp2 = m2.group();
                    int f1 = Integer.parseInt(temp1);
                    int f2 = Integer.parseInt(temp2);
                    if (f1 < f2) {
                        return 1;
                    } else if (f1 == f2) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });

        }
        return list;
    }

   /* public static List getBuildsFromFileSys(String path){
        File f = new File(path);

        List listF = new ArrayList<File>();
        File[] fList = f.listFiles();
        for(int i=0; i<fList.length; i++)
        {
            if(fList[i].isDirectory()){
                String tempStr = fList[i].getName();
                boolean isMatch = Pattern.matches(pattern, tempStr);
                if(isMatch)
                {
                    listF.add(fList[i]);
                }
            }
        }
        return listF;
    }*/

   /* public static List<String> getFileSort(String path) {

        List<File> list = getBuildsFromFileSys(path);
        List listStr = new ArrayList<String>();
        if (list != null && list.size() > 0) {

            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return 1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return -1;
                    }

                }
            });

        }
        for(File f : list){
            listStr.add(f.getName());
        }

        return listStr;
    }*/
}
