package backend;
import java.io.*;

public class UserReport{
    String userName, mobile, injuryDetails;
    String city, district, state, pincode, priority;
    public UserReport(String userName, String mobile, String injuryDetails,
                      String city, String district, String state,
                      String pincode, String priority){
        this.userName = userName;
        this.mobile = mobile;
        this.injuryDetails = injuryDetails;
        this.city = city;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
        this.priority = priority;
    }

    public void saveToFile(){
        try{
            File file = new File("reports.csv");
            if(file.length() == 0){
                FileWriter fw = new FileWriter(file, true);
                fw.write("Name,Mobile,Injury,City,District,State,Pincode,Status,Priority,NGO,NGOMobile\n");
                fw.close();
            }
            FileWriter fw = new FileWriter(file, true);
            fw.write("\n"+ userName + "," + mobile + "," + injuryDetails + "," +
                     city + "," + district + "," + state + "," + pincode +
                     ",Pending," + priority + ",NA,NA");
            fw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String getUserReport(String name, String mobile){
        StringBuilder result = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader("reports.csv"))){
            String line;
            while((line = br.readLine()) != null){
                if(line.isBlank()) continue;
                String[] data = line.split(",");
                if(data.length < 11) continue;
                if(data[0].trim().equalsIgnoreCase(name.trim()) && data[1].trim().equals(mobile.trim())){
                    result.append("Injury: ").append(data[2]).append("\n");
                    result.append("Priority: ").append(data[8]).append("\n");
                    result.append("Status: ").append(data[7]).append("\n");
                    if(data[7].equalsIgnoreCase("Resolved")){
                        result.append("Resolved By: ").append(data[9]).append("\n");
                        result.append("NGO Contact: ").append(data[10]).append("\n");
                        String ngoLocation = getNGOLocation(data[9]);
                        result.append(ngoLocation).append("\n\n");
                    }
                    else{
                        result.append("City: ").append(data[3]).append("\n");
                        result.append("District: ").append(data[4]).append("\n");
                        result.append("State: ").append(data[5]).append("\n");
                        result.append("Pincode: ").append(data[6]).append("\n\n");
                    }
                }
            }
        }
        catch(IOException e){
            return "Error reading file.";
        }
        return result.length() == 0 ? "No report found." : result.toString();
    }

    public static String getNGOLocation(String ngoName){
        try(BufferedReader br = new BufferedReader(new FileReader("ngo.csv"))){
            String line;
            while((line = br.readLine()) != null){
                if(line.isBlank()) continue;
                String[] data = line.split(",");
                if(data.length < 9) continue;
                if(data[2].trim().equalsIgnoreCase(ngoName.trim())){
                    return "NGO Location:\nCity: " + data[7] +
                           "\nDistrict: " + data[6] +
                           "\nState: " + data[5];
                }
            }
        }
        catch(IOException e){
            return "Error fetching NGO location.";
        }
        return "NGO location not found.";
    }

    public static String getNGOByLocation(String city, String district, String state, String pincode){
        StringBuilder cityList = new StringBuilder();
        StringBuilder pinList = new StringBuilder();
        StringBuilder districtList = new StringBuilder();
        StringBuilder stateList = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader("ngo.csv"))){
            String line;
            while((line = br.readLine()) != null){
                if(line.isBlank()) continue;
                String[] data = line.split(",");
                if(data.length < 9) continue;
                String nCity = data[7].trim();
                String nDistrict = data[6].trim();
                String nState = data[5].trim();
                String nPincode = data[8].trim();
                String formatted =
                        "Name: " + data[2] +
                        "\nMobile: " + data[3] + "\n\n";
                if(nCity.equalsIgnoreCase(city.trim())){
                    cityList.append(formatted);
                }
                else if(nPincode.equals(pincode.trim())){
                    pinList.append(formatted);
                }
                else if(nDistrict.equalsIgnoreCase(district.trim())){
                    districtList.append(formatted);
                }
                else if(nState.equalsIgnoreCase(state.trim())){
                    stateList.append(formatted);
                }
            }
        }
        catch(IOException e){
            return "Error reading NGO file.";
        }
        StringBuilder result = new StringBuilder();
        if(cityList.length() > 0){
            result.append("City NGOs:\n\n").append(cityList);
        }
        if(pinList.length() > 0){
            result.append("Pincode NGOs:\n\n").append(pinList);
        }
        if(districtList.length() > 0){
            result.append("District NGOs:\n\n").append(districtList);
        }
        if(stateList.length() > 0){
            result.append("State NGOs:\n\n").append(stateList);
        }
        if(result.length() == 0){
            return "No Nearby NGOs Found.";
        }
        return result.toString();
    }
}