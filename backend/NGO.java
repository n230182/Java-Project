package backend;
import java.io.*;

public class NGO{
    String email, password, name, mobile, govId;
    String state, district, city, pincode;
    public NGO(String email, String password, String name, String mobile,String govId, String state, 
        String district, String city, String pincode){
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

    public void saveToFile(){
        try{
            File file = new File("ngo.csv");
            if(file.length() == 0){
                FileWriter fw = new FileWriter(file, true);
                fw.write("Email,Password,Name,Mobile,GovId,State,District,City,Pincode\n");
                fw.close();
            }
            FileWriter fw = new FileWriter(file, true);
            fw.write("\n"+ email + "," + password + "," + name + 
                    "," + mobile + "," + govId + "," + state + "," 
                    + district + "," + city + "," + pincode);
            fw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String loginNGO(String email, String password){
        try (BufferedReader br = new BufferedReader(new FileReader("ngo.csv"))) {
            String line;
            while((line = br.readLine()) != null){
                if(line.isBlank()) continue;
                String[] data = line.split(",");
                if(data.length < 9) continue;
                if(data[0].trim().equals(email.trim()) &&
                    data[1].trim().equals(password.trim())){
                    return data[2] + "," + data[7] + "," + data[6] + "," +
                           data[5] + "," + data[8] + "," + data[3];
                }
            }
        }
        catch(IOException e){
            return null;
        }
        return null;
    }

    public static String getReportsForNGO(String city, String district, String state, String pincode){
        StringBuilder emergency = new StringBuilder();
        StringBuilder normal = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader("reports.csv"));
            String line;
            while((line = br.readLine()) != null){
                if(line.isBlank()) continue;
                String[] data = line.split(",");
                if(data.length < 11) continue;
                String City = data[3].trim();
                String District = data[4].trim();
                String State = data[5].trim();
                String Pincode = data[6].trim();
                String status = data[7].trim();
                String priority = data[8].trim();
                if(!status.equalsIgnoreCase("Pending")) continue;
                boolean match = false;
                if(City.equalsIgnoreCase(city.trim())) match = true;
                else if(Pincode.equals(pincode.trim())) match = true;
                else if(District.equalsIgnoreCase(district.trim())) match = true;
                else if(State.equalsIgnoreCase(state.trim())) match = true;
                if(match){
                    String formatted =
                            "Name: " + data[0] +
                            "\nMobile: " + data[1] +
                            "\nInjury: " + data[2] +
                            "\nCity: " + City +
                            "\nPriority: " + priority + "\n\n";
                    if(priority.equalsIgnoreCase("Emergency")){
                        emergency.append(formatted);
                    }
                    else{
                        normal.append(formatted);
                    }
                }
            }
            br.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "Emergency Cases:\n" + emergency.toString()
             + "\nNormal Cases:\n" + normal.toString();
    }

    public static void resolveReport(String ngoName, String ngoMobile, String userNumber){
        try {
            BufferedReader br = new BufferedReader(new FileReader("reports.csv"));
            StringBuilder updated = new StringBuilder();
            String line;
            while((line = br.readLine()) != null){
                if(line.isBlank()) continue;
                String[] data = line.split(",");
                if(data.length < 11) continue;
                if(data[1].trim().equals(userNumber.trim()) && data[7].equalsIgnoreCase("Pending")){
                    data[7] = "Resolved";
                    data[9] = ngoName;
                    data[10] = ngoMobile;
                }
                updated.append(String.join(",", data)).append("\n");
            }
            br.close();
            FileWriter fw = new FileWriter("reports.csv");
            fw.write(updated.toString());
            fw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}