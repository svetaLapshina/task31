import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class Main {
    public static void main(String[] args) {
        GameProgress gp1 = new GameProgress(1,2,3,4000);
        GameProgress gp2 = new GameProgress(4,5, 6,1000);
        GameProgress gp3 = new GameProgress(7,8,9,2000);

        String [] savedGames = {
                "games/save1.dat",
                "games/save2.dat",
                "games/save3.dat"
        };

        new File("games").mkdirs();
        saveGame(savedGames[0], gp1);
        saveGame(savedGames[1], gp2);
        saveGame(savedGames[2], gp3);

        zipFiles("games/saved.zip", savedGames);

        for (String p: savedGames) {
            new File(p).delete();
        }

    }

    public static void saveGame(String path, GameProgress obj) {
        try {
            FileOutputStream out = new FileOutputStream(path);
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(obj);
            oout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void zipFiles(String path, String[] pathToFiles) {
        try {
            byte[] buffer = new byte[1024];
            FileOutputStream fout = new FileOutputStream(path);
            ZipOutputStream zout = new ZipOutputStream(fout);

            for(int i=0; i < pathToFiles.length ; i++) {
                FileInputStream fin = new FileInputStream(pathToFiles[i]);
                zout.putNextEntry(new ZipEntry(new File(pathToFiles[i]).getName()));
                int length;
                while((length = fin.read(buffer)) > 0) {
                    zout.write(buffer, 0, length);
                }
                zout.closeEntry();
                fin.close();
            }
            zout.close();
        } catch(IOException ioe) {
            System.out.println("IOException :" + ioe);
        }
    }
}
