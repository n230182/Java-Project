package backend;

import java.io.*;

public class UserReport {

    String userName, mobile, injuryDetails;
    String city, photoPath, priority;

    public UserReport(String userName, String mobile, String injuryDetails,
                      String city, String photoPath, String priority) {

        this.userName = userName;
        this.mobile = mobile;
        this.injuryDetails = injuryDetails;
        this.city = city;
        this.photoPath = photoPath;
        this.priority = priority;
    }

    public void saveToFile() {

        try {
            FileWriter fw = new FileWriter("reports.csv", true);

            fw.write("\n"+userName + "," + mobile + "," + injuryDetails + "," +
                     city + "," + photoPath + "," +"Pending,"+ priority + ",NA,NA");

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUserReport(String name, String mobile) {

        StringBuilder result = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("reports.csv"))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) continue;

                String[] data = line.split(",");

                if (data[0].equalsIgnoreCase(name) && data[1].equals(mobile)) {

                    result.append("Injury: ").append(data[2]).append("\n");
                    result.append("Priority: ").append(data[6]).append("\n");
                    result.append("Status: ").append(data[5]).append("\n");

                    if(data[5].equalsIgnoreCase("Resolved")){
                        result.append("Resolved By: ").append(data[7]).append("\n");
                        result.append("NGO Contact: ").append(data[8]).append("\n");
                    }

                    result.append("City:").append(data[3]).append("\n");
                }
            }

        } catch (IOException e) {
            return "Error reading file.";
        }

        return result.length() == 0 ? "No report found." : result.toString();
    }

    public static String getNGOByCity(String city) {

    StringBuilder result = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new FileReader("ngo.csv"))) {

        String line;

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) continue;

            String[] data = line.split(",");

            if (data.length < 8) continue;

            if (data[7].trim().equalsIgnoreCase(city.trim())) {

                result.append("Name: ").append(data[2]).append("\n");
                result.append("Mobile: ").append(data[3]).append("\n\n");
            }
        }

    } catch (IOException e) {
        return "Error reading NGO file.";
    }

    return result.length() == 0 ? "No NGO found." : result.toString();  
    }
}