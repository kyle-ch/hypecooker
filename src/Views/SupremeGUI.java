package Views;

import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import Sites.Supreme;
import Sites.SupremeItem;

/**
 * GUI for Supreme bot.
 */
public class SupremeGUI extends JFrame {
  private String[] labelNames = {"First Name", "Last Name", "Email", "Phone Number", "Street Address",
          "Apartment", "Zip Code"};
  private String[] ccLabelNames = {"Credit Card Type", "Credit Card Number", "ccMonth", "ccYear", "ccv"};
  private String[] itemLabelNames = {"Category", "Keyword", "Color", "Size"};
  private Preferences pref = Preferences.userRoot().node(this.getClass().getName());
  private List<SupremeItem> items = new ArrayList<SupremeItem>();

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
      fields[i] = new JTextField(pref.get(labelNames[i], ""), 10);
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
    ccTypeBox.setEditable(true);
    ccTypeBox.setSelectedItem(pref.get(ccLabelNames[0], ""));
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
    fields[8].setText(pref.get(ccLabelNames[1], ""));
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
    fields[9].setText(pref.get(ccLabelNames[2], ""));
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
    fields[10].setText(pref.get(ccLabelNames[3], ""));
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
    fields[11].setText(pref.get(ccLabelNames[4], ""));
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

    Button saveButton = new Button("Save");
    JLabel saveLabel = new JLabel("Save", JLabel.TRAILING);
    saveLabel.setLabelFor(saveButton);
    String allLabels[] = ArrayUtils.addAll((ArrayUtils.addAll(labelNames, ccLabelNames)), itemLabelNames);
    ActionListener saveAction = e -> {
      for (int i = 0; i < allLabels.length; i++) {
        if (i == 7) {
          enteredData[i] = (String) ccTypeBox.getSelectedItem();
        } else if (i == 12) {
          enteredData[i] = (String) categoryBox.getSelectedItem();
        } else if (i == 15) {
          enteredData[i] = (String) sizeBox.getSelectedItem();
        } else {
          enteredData[i] = fields[i].getText();
        }
        pref.put(allLabels[i], enteredData[i]);
        System.out.println(enteredData[i]);
      }
      saveLabel.setText("SAVED!");
      panel.repaint();
    };
    saveButton.addActionListener(saveAction);

    JLabel itemsLoaded = new JLabel("No items loaded yet");
    Button addItem = new Button("Add Item");
    ActionListener addItemAction = e -> {
      SupremeItem s = new SupremeItem(enteredData[12], enteredData[13],
              enteredData[14], enteredData[15], sizeRequiredBox.getState());
      items.add(s);
      itemsLoaded.setText("Number of items loaded: " + items.size());
      for (SupremeItem i : items) {
        System.out.println(i.toString());
      }
    };

    addItem.addActionListener(saveAction);
    addItem.addActionListener(addItemAction);
    JLabel addItemLabel = new JLabel("Add Item", SwingConstants.TRAILING);

    Button removeLast = new Button("Remove");
    ActionListener removeLastAction = e -> {
      if (items.size() > 0) {
        items.remove(items.size() - 1);
      }
      itemsLoaded.setText("Number of items loaded: " + items.size());
    };
    removeLast.addActionListener(removeLastAction);
    JLabel removeLastLabel = new JLabel("Remove Last Item");



    Button submit = new Button("GO!");
    ActionListener submitAction = e -> {
      Supreme s = new Supreme(enteredData[0] + " " + enteredData[1], enteredData[2], enteredData[3],
              enteredData[4], enteredData[5], enteredData[6], enteredData[7], enteredData[8],
              enteredData[9], enteredData[10], enteredData[11], items);
      s.run();
    };
    submit.addActionListener(saveAction);
    submit.addActionListener(submitAction);


    submitPanel.add(saveLabel);
    submitPanel.add(saveButton);

    JLabel submitLabel = new JLabel("Submit", JLabel.TRAILING);
    submitPanel.add(submitLabel);
    submitPanel.add(submit);
    submitLabel.setLabelFor(submit);

    submitPanel.add(addItemLabel);
    submitPanel.add(addItem);
    addItemLabel.setLabelFor(addItem);

    submitPanel.add(removeLastLabel);
    submitPanel.add(removeLast);
    removeLastLabel.setLabelFor(removeLast);


    submitPanel.add(itemsLoaded);

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
