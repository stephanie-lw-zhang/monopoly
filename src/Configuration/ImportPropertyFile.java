package Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ImportPropertyFile {

        private Properties prop;
        private InputStream input;

        /***
         * Opens property file and loads it as a variable in the Screen classes
         * @param filepath name of properties file in resources
         */
        public ImportPropertyFile(String filepath) {
            System.out.print(filepath);
            prop = new Properties();
            input = ImportPropertyFile.class.getClassLoader().getResourceAsStream(filepath);
            try{
                prop.load(input);
            } catch (IOException e) {
                e.printStackTrace();     //apparently not supposed to just print stack trace but return an exception or something
            }
        }

        public String getProp(String aprop) {
            return prop.getProperty(aprop);
        }

    }


