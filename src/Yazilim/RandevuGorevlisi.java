package Yazilim;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

import javax.swing.*;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.*;


public class RandevuGorevlisi extends JFrame
{
    @SuppressWarnings("unused")
    private Connection conn;
    private static Connection dummy;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RandevuGorevlisi window = null;
                try {
                    window = new RandevuGorevlisi(dummy,"");
                    window.setVisible(true);

                }
                catch (Exception exp) {
                }
            }
        });
    }

    public RandevuGorevlisi(Connection conn,String kullanici)
    {
        this.conn = conn;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(368, 362);
        setTitle("Randevu Görevlisi");

        calendar = new Calendar();
        calendar.setTheme(ThemeType.Light);
        
        // Disable selection
        calendar.getSelection().setEnabled(false);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        nextButtonInToolbar(toolBar, imageFileNames[0]).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                calendar.setCurrentView(CalendarView.SingleMonth);
            }
        });

        nextButtonInToolbar(toolBar, imageFileNames[1]).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                calendar.setCurrentView(CalendarView.MonthRange);
            }
        });

        nextButtonInToolbar(toolBar, imageFileNames[2]).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                calendar.setCurrentView(CalendarView.List);
            }
        });

        nextButtonInToolbar(toolBar, imageFileNames[3]).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                calendar.setCurrentView(CalendarView.WeekRange);
            }
        });



        
        // Add click listener to the calendar
        calendar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleDateClick(e);
            }
        });
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(toolBar, BorderLayout.PAGE_START);
        cp.add(calendar, BorderLayout.CENTER);

        
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                exit();
            }
            public void windowOpened(WindowEvent e){
                onWindowOpened();
            }
        });
        
        nextButtonInToolbar(toolBar, "6.png").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAndOpenStartPage();
            }
        });

        // Initialize the date file
        _dataFile = new java.io.File("Schedule.dat").getAbsolutePath();
        
    }

    private void onWindowOpened() {
        if (new java.io.File(_dataFile).exists())
            calendar.getSchedule().loadFrom(_dataFile, ContentType.Xml);
    }
    
    private void closeAndOpenStartPage() {
        // Close the current window
        dispose();

        // Open a new StartPage
        StartPage startPage = new StartPage(conn);
        startPage.showFrame();
    }

    private void exit() {
        calendar.getSchedule().saveTo(_dataFile, ContentType.Xml);
    }

    private JButton nextButtonInToolbar(JToolBar bar, String imageName)
    {
        JButton button = new JButton(new ThumbnailAction(imagedir, imageName));
        button.setBorderPainted(false);
        button.setMargin(new Insets(5, 5, 5, 5));
        button.setSize(35, 35);
        
        bar.add(button);
        
        return button;
    }

    /**
     * List of all the image files to load.
     */
    private String[] imageFileNames = { "0.png", "1.png", "2.png", "3.png", "4.png", "5.png","6.png"};
 
    private String _dataFile;
    private Calendar calendar;
    private String imagedir = "Resources/";
    private static final long serialVersionUID = 1L;

 // Method to handle date click
    private void handleDateClick(MouseEvent e) {
        if (e.getClickCount() == 1) {
            Point p = e.getPoint();
            DateTime clickedDate = calendar.getDateAt(p);
            DateTime today = DateTime.today();

            // Check if clicked date is before today's date
            if (clickedDate.compareTo(today) <= 0) {
                JOptionPane.showMessageDialog(this, "Bugün ve daha öncesi seçilemez.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                RandevuGorevlisiGunSayfasi Rggs = new RandevuGorevlisiGunSayfasi(conn, clickedDate.toString());
                Rggs.setVisible(true);
            }
        }
    }

}

class ThumbnailAction extends AbstractAction
{
    public ThumbnailAction(String path, String imagePath)
    {
        // The LARGE_ICON_KEY is the key for setting the
        // icon when an Action is applied to a button.
        ImageIcon icon = createImageIcon(path, imagePath);
        putValue(LARGE_ICON_KEY, icon);
    }
    
    private ImageIcon createImageIcon(String path, String fileName) {
        java.net.URL imgURL = getClass().getResource(path + fileName);
        if (imgURL == null)
            imgURL = getClass().getResource("/" + path + fileName);

        if (imgURL != null) {
            return new ImageIcon(imgURL, fileName);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    
    private static final long serialVersionUID = 1L;
}
