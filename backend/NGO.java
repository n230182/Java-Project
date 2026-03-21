package backend;

import java.io.*;
import java.util.Scanner;

public class NGO {
    String email, password, name, mobile, govId;
    String state, district, city, pincode;

    public NGO(String email, String password, String name, String mobile,
               String govId, String state, String district, String city, String pincode) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
        this.govId = govId;
        this.state = state;
        this.district = district;
        this.city = city;
        this.pincode = pincode;
    }

    public void saveToFile() {
        try {
            FileWriter fw = new FileWriter("ngo.csv", true);

            fw.write("\n"+email + "," + password + "," + name + "," + mobile + "," +
                     govId + "," + state + "," + district + "," + city + "," + pincode);

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loginNGO(String email, String password) {

        try (BufferedReader br = new BufferedReader(new FileReader("ngo.csv"))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) continue;

                String[] data = line.split(",");

                if (data.length < 9) continue;

                if (data[0].equals(email) && data[1].equals(password)) {

                    return data[2] + "," + data[7] + "," + data[3]; // name, city, mobile
                }
            }

        } catch (IOException e) {
            return null;
        }

        return null;
    }

    public static String getReportsForNGO(String city){

    StringBuilder emergency = new StringBuilder();
    StringBuilder normal = new StringBuilder();

    try{
        Scanner sc = new Scanner(new File("reports.csv"));

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.isBlank()) continue;
            String[] data = line.split(",");
            String reportCity = data[3];
            String status = data[5];
            String priority = data[6];

            if(reportCity.equalsIgnoreCase(city) && status.equalsIgnoreCase("pending")){

                String formatted =
                        "Name: " + data[0] +
                        "\nMobile: " + data[1] +
                        "\nInjury: " + data[2] +
                        "\nStatus: " + status +
                        "\nPriority: " + priority + "\n\n";

                if(priority.equalsIgnoreCase("Emergency")){
                    emergency.append(formatted);
                } else {
                    normal.append(formatted);
                }
            }
        }

        sc.close();

    } catch(Exception e){
        e.printStackTrace();
    }

    return "Emergency Cases:\n" + emergency.toString()
         + "\n\nNormal Cases:\n" + normal.toString();
    }

    public static void resolveReport(String ngoName, String ngoMobile, String userNumber) {

        try {
            BufferedReader br = new BufferedReader(new FileReader("reports.csv"));
            StringBuilder updated = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) continue;

                String[] data = line.split(",");

                if (data[1].equalsIgnoreCase(userNumber) && data[5].equalsIgnoreCase("Pending")) {

                    data[5] = "Resolved";
                    data[7] = ngoName;
                    data[8] = ngoMobile;
                }

                updated.append(String.join(",", data)).append("\n");
            }

            br.close();

            FileWriter fw = new FileWriter("reports.csv");
            fw.write(updated.toString());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}