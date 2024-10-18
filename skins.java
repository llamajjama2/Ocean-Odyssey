import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * Represents a custom dialog for selecting game skins. It allows users to choose
 * from a variety of skins, including special unlocks like the Titanic skin, based
 * on certain game achievements.
 *
 * @author Justin, Haadi, Brian Y
 * @version 4.0
 */

public class skins extends JDialog implements ActionListener {
  private int width = 1000;
  private int length = 600;
  private int skinChosen = 0; //default
  private int titanicUnlock;
  private ImageIcon skinTitle;
  private ImageIcon closeImage;
  private ImageIcon pirateImage;
  private ImageIcon steamImage;
  private ImageIcon neonImage;
  private ImageIcon aquaImage;
  private ImageIcon titanicImage;
  private ImageIcon defaultImage;
  private JButton pirateButton;
  private JButton steamButton;
  private JButton neonButton;
  private JButton aquaButton;
  private JButton titanicButton;
  private JButton closeButton;
  private JButton defaultButton;
  private JFrame parent;

  /**
   * Constructs a skins dialog with the specified parent.
   * Initializes the UI components including skin selection buttons and their associated images.
   *
   * @param parent The parent frame from which the dialog is displayed.
   */
  public skins(JFrame parent) {
    super(parent, "Custom Popup", true);
    this.parent = parent;
    setSize(width, length);
    setLocationRelativeTo(parent);
    setUndecorated(true);
    JPanel popupPanel = new JPanel() {


      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("popupBackground.jpg"));
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
      }

    };
    popupPanel.setLayout(null);

    skinTitle = new ImageIcon(getClass().getResource("skinTitle.png"));
    JLabel titleImage = new JLabel(skinTitle);
    titleImage.setBounds((width - skinTitle.getIconWidth()) / 2, 0, skinTitle.getIconWidth(), skinTitle.getIconHeight()); // Set bounds for the label
    popupPanel.add(titleImage);

    closeImage = new ImageIcon(getClass().getResource("close.png"));
    pirateImage = new ImageIcon(getClass().getResource("Pirate.png"));
    steamImage = new ImageIcon(getClass().getResource("Steampunk.png"));
    neonImage = new ImageIcon(getClass().getResource("NeonGlow.png"));
    aquaImage = new ImageIcon(getClass().getResource("AquaticExplorer.png"));
    int buttonX = 0;
    int buttonY = (length) / 2;

    closeButton = new CustomButton(closeImage);
    closeButton.setPreferredSize(new Dimension(closeImage.getIconWidth(), closeImage.getIconHeight()));
    closeButton.addActionListener(e -> dispose());
    closeButton.setContentAreaFilled(false);
    closeButton.setBounds(0, 0, closeImage.getIconWidth(), closeImage.getIconHeight());
    popupPanel.add(closeButton);

    pirateButton = new CustomButton(pirateImage);
    pirateButton.setPreferredSize(new Dimension(pirateImage.getIconWidth(), pirateImage.getIconHeight()));
    pirateButton.setContentAreaFilled(false);
    pirateButton.addActionListener(this);
    pirateButton.setBounds(buttonX, buttonY, pirateImage.getIconWidth(), pirateImage.getIconHeight());
    popupPanel.add(pirateButton);

    steamButton = new CustomButton(steamImage);
    steamButton.setPreferredSize(new Dimension(steamImage.getIconWidth(), steamImage.getIconHeight()));
    steamButton.setContentAreaFilled(false);
    steamButton.addActionListener(this);
    steamButton.setBounds(buttonX + 200, buttonY, steamImage.getIconWidth(), steamImage.getIconHeight());
    popupPanel.add(steamButton);

    neonButton = new CustomButton(neonImage);
    neonButton.setPreferredSize(new Dimension(neonImage.getIconWidth(), neonImage.getIconHeight()));
    neonButton.setContentAreaFilled(false);
    neonButton.addActionListener(this);
    neonButton.setBounds(buttonX + 400, buttonY, neonImage.getIconWidth(), neonImage.getIconHeight());
    popupPanel.add(neonButton);

    aquaButton = new CustomButton(aquaImage);
    aquaButton.setPreferredSize(new Dimension(aquaImage.getIconWidth(), aquaImage.getIconHeight()));
    aquaButton.setContentAreaFilled(false);
    aquaButton.addActionListener(this);
    aquaButton.setBounds(buttonX + 600, buttonY, aquaImage.getIconWidth(), aquaImage.getIconHeight());
    popupPanel.add(aquaButton);

    if (checkTitanicUnlock()) {
      titanicImage = new ImageIcon(getClass().getResource("titanic.png"));
    } else {
      titanicImage = new ImageIcon(getClass().getResource("titanicL.png"));
    }

    titanicButton = new CustomButton(titanicImage);
    titanicButton.setPreferredSize(new Dimension(titanicImage.getIconWidth(), titanicImage.getIconHeight()));
    if (checkTitanicUnlock()) titanicButton.addActionListener(this);
    titanicButton.setContentAreaFilled(false);
    titanicButton.setBounds(buttonX + 800, buttonY, titanicImage.getIconWidth(), titanicImage.getIconHeight());
    popupPanel.add(titanicButton);
    setContentPane(popupPanel);
  }

  /**
   * Responds to button clicks within the dialog, applying the selected skin to the user's
   * profile and saving the selection as necessary. It includes special handling for skins
   * that may require unlock conditions, such as the Titanic skin.
   *
   * @param e The action event triggered by clicking a button.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == pirateButton) {
      Save.getUser().setCurrentSkin(new Skin("Pirate.png", 0, "Common"));
      if (!Save.getUser().getUsername().equals("Guest")) {
          try {
              Save.saveData();
          } catch (IOException ex) {
              throw new RuntimeException(ex);
          }
      }
      parent.dispose();
      profile p = new profile();
      dispose();
    } else if (e.getSource() == steamButton) {
      Save.getUser().setCurrentSkin(new Skin("Steampunk.png", 0, "Common"));
      if (!Save.getUser().getUsername().equals("Guest")) {
        try {
          Save.saveData();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
      parent.dispose();
      profile p = new profile();
      dispose();
    } else if (e.getSource() == neonButton) {
        Save.getUser().setCurrentSkin(new Skin("NeonGlow.png", 0, "Common"));
      if (!Save.getUser().getUsername().equals("Guest")) {
        try {
          Save.saveData();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
      parent.dispose();
      profile p = new profile();
      dispose();

    } else if (e.getSource() == aquaButton) {
      Save.getUser().setCurrentSkin(new Skin("AquaticExplorer.png", 0, "Common"));
      if (!Save.getUser().getUsername().equals("Guest")) {
        try {
          Save.saveData();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
      parent.dispose();
      profile p = new profile();
      dispose();

    } else if (e.getSource() == titanicButton) {
      Save.getUser().setCurrentSkin(new Skin("Titanic.png", 0, "Legendary"));
      if (!Save.getUser().getUsername().equals("Guest")) {
        try {
          Save.saveData();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
      parent.dispose();
      profile p = new profile();
      dispose();
    }
  }

  /**
   * Checks whether the Titanic skin is unlocked for the current user.
   *
   * @return true if the Titanic skin is available for selection, false otherwise.
   */
  private boolean checkTitanicUnlock() {
    List<Skin> skins = Save.getUser().getSkins();
    for (Skin skin : skins) {
      if (skin.getName().equals("Titanic.png")) {
        return true;
      }
    }
    return false;
  }
}