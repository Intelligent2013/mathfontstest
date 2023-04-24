import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.*;

public class MathFontsTest extends JFrame {

    private static List<String> registeredFonts = new ArrayList<>();

    public static void main(String[] args) {

        registerFonts(Paths.get(System.getProperty("user.home"), ".fontist", "fonts"));

        JFrame frame = new JFrame("Math fonts rendering examples");
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 500);

        Container c = frame.getContentPane();

        JLabel label1 = new JLabel("Cambria Math: [a, b]");
        label1.setBounds(50, 50, 400, 30);
        label1.setFont(new Font("Cambria Math", Font.PLAIN, 30));

        JLabel label2 = new JLabel("Cambria Math: [a, b]");
        label2.setBounds(50, 100, 400, 30);
        label2.setFont(new Font("Cambria Math", Font.BOLD, 30));

        JLabel label3 = new JLabel("STIX Two Math: [a, b]");
        label3.setBounds(50, 150, 400, 30);
        label3.setFont(new Font("STIX Two Math", Font.PLAIN, 30));

        JLabel label4 = new JLabel("STIX Two Math: [a, b]");
        label4.setBounds(50, 200, 400, 30);
        label4.setFont(new Font("STIX Two Math", Font.BOLD, 30));

        c.add(label1);
        c.add(label2);
        c.add(label3);
        c.add(label4);
    }

    private static void registerFonts(Path fonts_path) {
        System.out.println(fonts_path.toString());

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        try (Stream<Path> walk = Files.walk(fonts_path)) {
            List<String> fontfiles = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".ttf") ||
                            f.endsWith(".TTF") ||
                            f.endsWith(".ttc") ||
                            f.endsWith(".TTC") ||
                            f.endsWith(".otf") ||
                            f.endsWith(".OTF"))
                    .collect(Collectors.toList());

            fontfiles.forEach(fontfile -> {
                registerFont(ge, fontfile);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registerFont(GraphicsEnvironment ge, String fontFile){
        if (!registeredFonts.contains(fontFile)) {
            boolean isError = false;
            if (ge == null) {
                ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            }
            try {
                // try to obtain font to check an exception:
                // java.lang.NullPointerException: Cannot load from short array because "sun.awt.FontConfiguration.head" is null
                String[] names = ge.getAvailableFontFamilyNames();
                int count = names.length;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
            try {
                Font ttfFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontFile));
                //register the font
                ge.registerFont(ttfFont);
                System.out.println("Registering font: " + fontFile + ", " + ttfFont.toString());
                registeredFonts.add(fontFile);
            } catch(FontFormatException e) {
                try {
                    Font type1Font = Font.createFont(Font.TYPE1_FONT, new File(fontFile));
                    //register the font
                    ge.registerFont(type1Font);
                    System.out.println("Registering font: " + fontFile + ", " + type1Font.toString());
                    registeredFonts.add(fontFile);
                } catch(FontFormatException e1) {
                    isError = true;
                    e1.printStackTrace();
                }  catch (IOException e2) {
                    isError = true;
                    e2.printStackTrace();
                }
            } catch (IOException e) {
                isError = true;
                e.printStackTrace();
            }
            if (isError) {
                System.out.println("WARNING: error in the font " + fontFile + " registering.");
            }
        }
    }
}
