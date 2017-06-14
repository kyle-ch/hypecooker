package Views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import Sites.Supreme;

/**
 * GUI for Supreme bot.
 */
public class SupremeGUI extends JFrame {
  private String[] labelNames = {"First Name", "Last Name", "Email", "Phone Number", "Street Address",
          "Apartment", "Zip Code"};
  private String[] ccLabelNames = {"Credit Card Type", "Credit Card Number", "ccMonth", "ccYear", "ccv"};
  private String[] itemLabelNames = {"Category", "Keyword", "Color", "Size"};

  /**
   * Method to render the gui.
   */
  public void render() {
    this.setTitle("Supreme");
    JTextField[] fields = new JTextField[16];
    JLabel[] labels = new JLabel[16];
    String[] enteredData = new String[16];
    JPanel panel = new JPanel(new SpringLayout());
    for (int i = 0; i < labelNames.length; i++) {
      labels[i] = new JLabel(labelNames[i], JLabel.TRAILING);
      fields[i] = new JTextField(10);
      labels[i].setLabelFor(fields[i]);
      panel.add(labels[i]);
      panel.add(fields[i]);
    }

    SpringUtilities.makeCompactGrid(panel,
            labelNames.length, 2, //rows, cols
            6, 6,        //initX, initY
            6, 6);       //xPad, yPad

    // CC Panel
    JPanel ccPanel = new JPanel(new SpringLayout());

    JLabel ccTypeLabel = new JLabel(ccLabelNames[0], JLabel.TRAILING);
    String[] types = {"Visa", "American Express", "Mastercard"};
    JComboBox<String> ccTypeBox = new JComboBox(types);
    ccTypeBox.addActionListener(e -> enteredData[7] = (String) ccTypeBox.getSelectedItem());
    ccTypeLabel.setLabelFor(ccTypeBox);
    ccPanel.add(ccTypeLabel);
    ccPanel.add(ccTypeBox);

    MaskFormatter ccNumberFmt = null;
    try {
      ccNumberFmt = new MaskFormatter("################");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    fields[8] = new JFormattedTextField(ccNumberFmt);
    fields[8].setColumns(16);
    labels[8] = new JLabel(ccLabelNames[1], JLabel.TRAILING);
    labels[8].setLabelFor(fields[8]);
    ccPanel.add(labels[8]);
    ccPanel.add(fields[8]);

    JPanel ccDatePanel = new JPanel();
    MaskFormatter ccMonthFmt = null;
    try {
      ccMonthFmt = new MaskFormatter("##");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    fields[9] = new JFormattedTextField(ccMonthFmt);
    fields[9].setColumns(2);
    labels[9] = new JLabel("MM/YYYY", JLabel.TRAILING);
    labels[9].setLabelFor(ccDatePanel);
    ccPanel.add(labels[9]);
    ccDatePanel.add(fields[9]);

    MaskFormatter ccYearFmt = null;
    try {
      ccYearFmt = new MaskFormatter("####");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    fields[10] = new JFormattedTextField(ccYearFmt);
    fields[10].setColumns(4);
    ccDatePanel.add(fields[10]);

    ccPanel.add(ccDatePanel);

    MaskFormatter ccvFmt = null;
    try {
      ccvFmt = new MaskFormatter("###");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    fields[11] = new JFormattedTextField(ccvFmt);
    fields[11].setColumns(4);
    labels[11] = new JLabel(ccLabelNames[4], JLabel.TRAILING);
    labels[11].setLabelFor(fields[11]);
    ccPanel.add(labels[11]);
    ccPanel.add(fields[11]);

    SpringUtilities.makeCompactGrid(ccPanel,
            ccLabelNames.length - 1, 2, //rows, cols
            6, 6,        //initX, initY
            6, 6);


    // Item Info Panel
    JPanel itemPanel = new JPanel(new SpringLayout());

    labels[12] = new JLabel(itemLabelNames[0], JLabel.TRAILING);
    String[] categories = {"jackets", "shirts", "tops_sweaters", "sweatshirts", "pants", "shorts",
            "t-shirts", "hats", "bags", "accessories", "shoes", "skate"};
    JComboBox<String> categoryBox = new JComboBox(categories);
    categoryBox.addActionListener(e -> enteredData[12] = (String) categoryBox.getSelectedItem());
    labels[12].setLabelFor(categoryBox);
    itemPanel.add(labels[12]);
    itemPanel.add(categoryBox);

    labels[13] = new JLabel(itemLabelNames[1], JLabel.TRAILING);
    fields[13] = new JTextField(10);
    labels[13].setLabelFor(fields[13]);
    itemPanel.add(labels[13]);
    itemPanel.add(fields[13]);

    labels[14] = new JLabel(itemLabelNames[2], JLabel.TRAILING);
    fields[14] = new JTextField(10);
    labels[14].setLabelFor(fields[14]);
    itemPanel.add(labels[14]);
    itemPanel.add(fields[14]);

    labels[15] = new JLabel(itemLabelNames[3], JLabel.TRAILING);
    String[] sizes = {"Small", "Medium", "Large", "XLarge"};
    JComboBox<String> sizeBox = new JComboBox(sizes);
    sizeBox.addActionListener(e -> enteredData[15] = (String) sizeBox.getSelectedItem());
    labels[15].setLabelFor(sizeBox);
    itemPanel.add(labels[15]);
    itemPanel.add(sizeBox);

    JLabel sizeRequiredLabel = new JLabel("Size Required?", JLabel.TRAILING);
    Checkbox sizeRequiredBox = new Checkbox();
    sizeRequiredLabel.setLabelFor(sizeRequiredBox);
    itemPanel.add(sizeRequiredLabel);
    itemPanel.add(sizeRequiredBox);




    SpringUtilities.makeCompactGrid(itemPanel,
            itemLabelNames.length + 1, 2, //rows, cols
            6, 6,        //initX, initY
            6, 6);


    // Submit Panel
    JPanel submitPanel = new JPanel();
    Button submit = new Button("GO!");
    ActionListener submitAction = e -> {
      for (int i = 0; i < labelNames.length + ccLabelNames.length + itemLabelNames.length; i++) {
        if (i == 7) {
          enteredData[i] = (String) ccTypeBox.getSelectedItem();
        } else if (i == 12) {
          enteredData[i] = (String) categoryBox.getSelectedItem();
        } else if (i == 15) {
          enteredData[i] = (String) sizeBox.getSelectedItem();
        } else {
          enteredData[i] = fields[i].getText();
        }
        System.out.println(enteredData[i]);
      }
      Supreme s = new Supreme(enteredData[0] + " " + enteredData[1], enteredData[2], enteredData[3],
              enteredData[4], enteredData[5], enteredData[6], enteredData[7], enteredData[8],
              enteredData[9], enteredData[10], enteredData[11], enteredData[12], enteredData[13],
              enteredData[14], enteredData[15], sizeRequiredBox.getState());
      s.run();
    };
    submit.addActionListener(submitAction);

    Button resetButton = new Button("Reset");
    resetButton.addActionListener(e -> {
      submit.removeActionListener(submit.getActionListeners()[0]);
      submit.setLabel("GO!");
      submit.addActionListener(submitAction);
    });
    JLabel resetLabel = new JLabel("Reset", JLabel.TRAILING);
    resetLabel.setLabelFor(resetButton);

    JLabel submitLabel = new JLabel("Submit", JLabel.TRAILING);
    submitPanel.add(submitLabel);
    submitPanel.add(submit);
    submitLabel.setLabelFor(submit);

    submitPanel.add(resetLabel);
    submitPanel.add(resetButton);

    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

    container.add(panel);
    container.add(ccPanel);
    container.add(itemPanel);
    container.add(submitPanel);

    this.add(container);
    this.pack();
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
