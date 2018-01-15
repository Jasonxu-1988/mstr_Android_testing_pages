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
import java.util.regex.Pattern;

@RestController
public class GreetingController {

    private static final String filePath = "/Users/bxu/Desktop/Jason/Workspaces/Testdir";
    private static final String pattern = "^10.11.1000.*";
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

    public static List getBuildsFromFileSys(String path){
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
    }

    public static List<String> getFileSort(String path) {

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
    }
}
