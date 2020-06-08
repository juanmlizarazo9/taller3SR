import com.google.gson.*;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.JSONObject;
import net.sf.json.util.*;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class MoviesLoad3 {

    static HashMap<String, String> movies = null;

    public static String www =  "https://www.imdb.com/title/tt";
    public static void main(String[] args) {

        movies = new HashMap<String, String>();
        String linea = "";
        try {
            String file = "./data/links2.csv";
            BufferedReader abr = new BufferedReader(new FileReader(file));
            boolean lee = false;
            int cont = 0;
            while((linea=abr.readLine())!=null){
                if(lee==true) {
                    String[] nuevo = linea.split(",");
                    String idPeliculaIMDB = nuevo[1];
                    System.out.println("ID en IMDB: " + idPeliculaIMDB);
                    String director = getDirector(idPeliculaIMDB);
                    movies.put(idPeliculaIMDB, director);
                    cont++;
                }
                lee=true;
                System.out.println("Elementos cargados: " + cont);
            }
            System.out.println("Se cargó el árbol con: " + movies.size() + " Elementos");
            System.out.println(movies.get("0114709"));
            crearCSV(movies);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String getDirector(String movieID) {
        String nname = "Not Found";
        String aactor = "Not found";
        System.out.println("Método get director");
        try {
            //String dir = "https://www.imdb.com/title/tt0086756";
            int movieIDl = movieID.length();
            System.out.println("numero de caracteres: "+movieIDl);
            if(movieIDl==6){
                movieID = "0"+movieID;
            } if(movieIDl==5){
                movieID = "00"+movieID;
            } if(movieIDl==4){
                movieID= "000"+movieID;
            }
            String dir = www+movieID;

            System.out.println("Url del request: " + dir);
            URL url = new URL(dir);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/ld+json");
            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output;
                String total = "{";
                System.out.println("Output from Server .... \n");
                String inicio = "<script type=\"application/ld+json\">{";
                String fin1 = "}</script>            <link rel=\"canonical\" href=\"https://www.imdb.com/title/tt1099212/\">";
                String fin2 = "}</script>";
                boolean encontro = false;
                while ((output = br.readLine()) != null) {
                    if (output.equals(fin1) || output.equals(fin2)) {
                        encontro = false;
                        total += "}";
                    }
                    if (encontro == true) {
                        total += output + "\n";
                    }
                    if (output.equals(inicio)) {
                        encontro = true;
                    }
                }
                JsonObject ob = new Gson().fromJson(total, JsonObject.class);
                Object a = ob.get("director");
                Object b = ob.get("actor");
                if (a != null) {
                    if (a.getClass() == JsonArray.class) {
                        System.out.println("Success");
                    }
                    if (a.getClass() == JsonObject.class) {

                        JsonObject director = ob.get("director").getAsJsonObject();
                        String name = director.get("name").toString();
                        nname = name.replace("\"", "");
                        System.out.println("Director de : " + movieID + " : " + nname);
                    } else if (a.getClass() == JsonArray.class) {
                        JsonArray director = ob.get("director").getAsJsonArray();
                        String name = "";
                        for (int i = 0; i < director.size(); i++) {
                            JsonElement objects = director.get(i);
                            JsonObject obj = objects.getAsJsonObject();
                            String aux = obj.get("name").toString();
                            String aux2 = aux.replace("\"", "");
                            if (i == director.size() - 1) {
                                name += aux2;
                            } else {
                                name += aux2 + "|";
                            }
                        }
                        nname = name;
                        System.out.println("Director de : " + movieID + " : " + nname);
                    }

                }
                if(b!=null){
                    if (b.getClass() == JsonArray.class) {
                        System.out.println("Success");
                    }
                    if (b.getClass() == JsonObject.class) {

                        JsonObject director = ob.get("actor").getAsJsonObject();
                        String name = director.get("name").toString();
                        aactor = name.replace("\"", "");
                        System.out.println("Actores de : " + movieID + " : " + nname);
                    } else if (b.getClass() == JsonArray.class) {
                        JsonArray director = ob.get("actor").getAsJsonArray();
                        String name = "";
                        for (int i = 0; i < director.size(); i++) {
                            JsonElement objects = director.get(i);
                            JsonObject obj = objects.getAsJsonObject();
                            String aux = obj.get("name").toString();
                            String aux2 = aux.replace("\"", "");
                            if (i == director.size() - 1) {
                                name += aux2;
                            } else {
                                name += aux2 + "|";
                            }
                        }
                        aactor = name;
                        System.out.println("Actor de : " + movieID + " : " + aactor);
                    }
                }
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
        return nname+","+aactor;

    }


    public static void crearCSV(HashMap<String, String> map){
        String eol = System.getProperty("line.separator");

        try (Writer writer = new FileWriter("directores2.csv")) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.append(entry.getKey())
                        .append(',')
                        .append(entry.getValue())
                        .append(eol);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }




}