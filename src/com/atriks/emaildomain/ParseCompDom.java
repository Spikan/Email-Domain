package com.atriks.emaildomain;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class ParseCompDom {


    public static ArrayList parse() throws IOException {
        ArrayList<CompDom> list = new ArrayList<CompDom>();


        String fileName = "C:\\Users\\Programmer\\Documents\\Dan's Code\\Java\\GoogleDomain\\input6.txt";
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);

        scanner.useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()) {
            String line = scanner.next();
            StringTokenizer st = new StringTokenizer(line);
            if (st.countTokens() == 2) {
                String company = st.nextToken();
                String domain = st.nextToken();
                CompDom cd = new CompDom(company, domain);
                list.add(cd);
            }

        }
        scanner.close();
        return list;
    }


}
