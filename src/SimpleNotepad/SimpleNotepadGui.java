package SimpleNotepad;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author Chase Stevens
 */
public class SimpleNotepadGui extends javax.swing.JFrame {
    
    /**
     * Constructs a new NotepadGui object.
     * Initializes the Notepad GUI window with a title "Simple Notepad", sets an icon from the Simple_Notepad_icon.png file.
     * initializes components, creates a context menu popup, sets up an undo manager for the text area.
     * Disables default key bindings, and enables word wrapping for the text area.
     */
    public SimpleNotepadGui() {
        setTitle("Simple Notepad");
        try {
            URL iconURL = getClass().getResource("Simple_Notepad_icon.png");
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Icon not Found", "Error",JOptionPane.ERROR_MESSAGE);
        }
        initComponents(); 
        createContextPopupMenu();
        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);
        disableDefaultKeybinds();
        wordWrap(); 
    }
        
    /**
     * Initializes and configures the components of the GUI.
     * This method sets up a JTextArea within a JScrollPane for text input.
     * This method also sets up a JMenuBar with File, Edit, and View menus, and various menu items.
     */                         
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        fileMenuBar = new javax.swing.JMenu();
        newFileMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        fileMenuSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenuBar = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        redoMenuItem = new javax.swing.JMenuItem();
        editMenuSeparator1 = new javax.swing.JPopupMenu.Separator();
        findMenuItem = new javax.swing.JMenuItem();
        replaceMenuItem = new javax.swing.JMenuItem();
        editMenuSeparator2 = new javax.swing.JPopupMenu.Separator();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        selectAllMenuItem = new javax.swing.JMenuItem();
        viewMenuBar = new javax.swing.JMenu();
        darkModeMenuItem = new javax.swing.JCheckBoxMenuItem();
        wordWrapMenuItem = new javax.swing.JCheckBoxMenuItem();
        fontMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textArea.setColumns(20);
        textArea.setFont(new java.awt.Font("Consolas", 0, 16)); // NOI18N
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        fileMenuBar.setText("File");

        newFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        newFileMenuItem.setText("New");
        newFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileMenuItemActionPerformed(evt);
            }
        });
        fileMenuBar.add(newFileMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenuBar.add(saveMenuItem);

        saveAsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveAsMenuItem.setText("Save As");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        fileMenuBar.add(saveAsMenuItem);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenuBar.add(openMenuItem);
        fileMenuBar.add(fileMenuSeparator1);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenuBar.add(exitMenuItem);

        menuBar.add(fileMenuBar);

        editMenuBar.setText("Edit");

        undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        undoMenuItem.setText("Undo");
        undoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoMenuItemActionPerformed(evt);
            }
        });
        editMenuBar.add(undoMenuItem);

        redoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        redoMenuItem.setText("Redo");
        redoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoMenuItemActionPerformed(evt);
            }
        });
        editMenuBar.add(redoMenuItem);
        editMenuBar.add(editMenuSeparator1);

        findMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        findMenuItem.setText("Find");
        findMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findMenuItemActionPerformed(evt);
            }
        });
        editMenuBar.add(findMenuItem);

        replaceMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        replaceMenuItem.setText("Replace");
        replaceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceMenuItemActionPerformed(evt);
            }
        });
        editMenuBar.add(replaceMenuItem);
        editMenuBar.add(editMenuSeparator2);

        cutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        cutMenuItem.setText("Cut");
        cutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuItemActionPerformed(evt);
            }
        });
        editMenuBar.add(cutMenuItem);

        copyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        copyMenuItem.setText("Copy");
        copyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuItemActionPerformed(evt);
            }
        });
        editMenuBar.add(copyMenuItem);

        pasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        pasteMenuItem.setText("Paste");
        pasteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteMenuItemActionPerformed(evt);
            }
        });
        editMenuBar.add(pasteMenuItem);

        deleteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        deleteMenuItem.setText("Delete");
        deleteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        editMenuBar.add(deleteMenuItem);

        selectAllMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        selectAllMenuItem.setText("Select All");
        selectAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllMenuItemActionPerformed(evt);
            }
        });
        editMenuBar.add(selectAllMenuItem);

        menuBar.add(editMenuBar);

        viewMenuBar.setText("View");

        darkModeMenuItem.setText("Dark Mode");
        darkModeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                darkModeMenuItemActionPerformed(evt);
            }
        });
        viewMenuBar.add(darkModeMenuItem);

        wordWrapMenuItem.setSelected(true);
        wordWrapMenuItem.setText("Word Wrap");
        wordWrapMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wordWrapMenuItemActionPerformed(evt);
            }
        });
        viewMenuBar.add(wordWrapMenuItem);

        fontMenuItem.setText("Font");
        fontMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontMenuItemActionPerformed(evt);
            }
        });
        viewMenuBar.add(fontMenuItem);

        menuBar.add(viewMenuBar);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1294, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );

        pack();
    }         
     
    /**
     * Creates a context popup menu with various editing options such as Undo,Redo, Cut, Copy, Paste, Delete, and Select All.
     * Each menu item is assigned an accelerator key and an action listener to handle the respective action when triggered.
     * The popup menu is displayed upon right-clicking in the text area. 
     */
    private void createContextPopupMenu() {
        popupMenu = new JPopupMenu();
        
        JMenuItem undoContextPopupMenuItem = new JMenuItem("Undo");
        undoContextPopupMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
        undoContextPopupMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                undoMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(undoContextPopupMenuItem);
        
        JMenuItem redoContextPopupMenuItem = new JMenuItem("Redo");
        redoContextPopupMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
        redoContextPopupMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                redoMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(redoContextPopupMenuItem);
        popupMenu.addSeparator();
        JMenuItem cutContextPopupMenuItem = new JMenuItem("Cut");
        cutContextPopupMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        cutContextPopupMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cutMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(cutContextPopupMenuItem);

        JMenuItem copyContextPopupMenuItem = new JMenuItem("Copy");
        copyContextPopupMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        copyContextPopupMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                copyMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(copyContextPopupMenuItem);

        JMenuItem pasteContextPopupMenuItem = new JMenuItem("Paste");
        pasteContextPopupMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        pasteContextPopupMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pasteMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(pasteContextPopupMenuItem);

        JMenuItem deleteContextPopupMenuItem = new JMenuItem("Delete");
        deleteContextPopupMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        deleteContextPopupMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(deleteContextPopupMenuItem);

        JMenuItem selectAllContextPopupMenuItem = new JMenuItem("Select All");
        selectAllContextPopupMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        selectAllContextPopupMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                selectAllMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(selectAllContextPopupMenuItem);
        
        textArea.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitProgram();
            }
        });
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Disables the default key bindings for Ctrl+H in the text area component.
     * This allows the shortcut key for the replaceMenuItemActionPerformed method.
     * @param textArea the JTextArea component for which to disable the Ctrl+H key bindings.
     */
    private void disableDefaultKeybinds() {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_H, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
        textArea.getInputMap(JComponent.WHEN_FOCUSED).put(keyStroke, "none");
    }
    
    /**
     * Handles the action performed for the "New File" menu item.
     * If the text area is not empty, prompts the user to save the file before clearing the text area.
     * 
     * @param evt The action event triggered by the "New File" menu item.
     */
    private void newFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                
        if (!textArea.getText().isEmpty()) {
            int exitMenuResult = JOptionPane.showConfirmDialog(this,"Do you want to save the file before exiting?","Save File",JOptionPane.YES_NO_CANCEL_OPTION);
            switch (exitMenuResult) {
                case JOptionPane.YES_OPTION:
                    //Possibly just add saveDialogBox's code here and get rid of successfulSave variable.
                    saveDialogBox();
                    if (successfulSave == true) {
                        textArea.setText("");
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    textArea.setText("");
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;
                default:
                    break;
            }  
        }
    } 

    /**
     * Displays a dialog box for saving a text file, allowing the user to specify the file path. 
     * Automatically appends ".txt" to the file name if not already present. 
     * Sets the icon image of the dialog box toNotepad_icon.png" if found.
     *
     * @throws FileNotFoundException if the specified file path is invalid or inaccessible.
     * @throws IOException if an I/O error occurs while closing the output stream.
     */
    private void saveDialogBox() {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files (*.txt)", "txt");
        fc.setFileFilter(filter);
        try {
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fc.getSelectedFile().getAbsolutePath();
                if (fc.getFileFilter() == filter && !filePath.toLowerCase().endsWith(".txt")) {
                    filePath += ".txt";
                }
                FileOutputStream fs = new FileOutputStream(filePath);
                PrintWriter outFS = new PrintWriter(fs);
                outFS.println(textArea.getText());
                fileLocation = filePath;
                outFS.close();
                fs.close();
                successfulSave = true;
            } else {
                successfulSave = false;
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Caught FileNotFoundException. Try again making sure the file name and path are correct.", "Error",JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Caught IOException when closing output stream. Try again.", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the action performed for the "save" menu item.
     * Saves the content of a text area to a file. If a file location has not been set, opens a save dialog box to prompt the user for a file location.
     *
     * @throws FileNotFoundException if the specified file path is invalid or inaccessible.
     * @throws IOException if an I/O error occurs while closing the output stream.
     * 
     * @param evt The action event triggered by the "save" menu item.
     */
    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        if (fileLocation == null) {
            saveDialogBox();
        } else {
            try {
                FileOutputStream fs = new FileOutputStream(fileLocation);
                PrintWriter outFS = new PrintWriter(fs);
                outFS.println(textArea.getText());
                outFS.close();
                fs.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Caught FileNotFoundException. Try again making sure the file name and path are correct.", "Error",JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Caught IOException when closing output stream. Try again.", "Error",JOptionPane.ERROR_MESSAGE);
            }    
        }
    }                                            
    
    /**
     * Handles the action performed for the "Save as" menu item.
     * Saves the content of a text area to a file. Opens a save dialog box to prompt the user for a file location.
     * 
     * @param evt The action event triggered by the "Save as" menu item.
     */
    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                               
        saveDialogBox();
    }                                              

    /**
     * Handles the action performed for the "open" menu item.
     * Opens a file chooser dialog box to allow the user to select a text file to open, then loads the selected file into the text area.
     * 
     * @throws IOException if an I/O error occurs while closing the output stream.
     * 
     * @param evt The action event triggered by the "open" menu item.
     */
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));   
        int openMenuResult = fc.showOpenDialog(fc);
        if (openMenuResult == JFileChooser.APPROVE_OPTION) {
            Path fileName = Path.of(fc.getSelectedFile().getAbsolutePath());
            try {
                textArea.setText(Files.readString(fileName));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Caught IOException when closing output stream. Try again.", "Error",JOptionPane.ERROR_MESSAGE);   
            }
        }
    }                                            
    
    /**
     * Checks if the text area is empty or not and handles the exit action accordingly. 
     * If the text area is empty, it closes the program. 
     * If the text area is not empty, it prompts the user to save the file before exiting.
     */
    private void exitProgram() {                                             
        if(textArea.getText().isEmpty()) {
            dispose();
        } else if (!textArea.getText().isEmpty()) {
            int exitMenuResult = JOptionPane.showConfirmDialog(this,"Do you want to save the file before exiting?","Save File",JOptionPane.YES_NO_CANCEL_OPTION);
            switch (exitMenuResult) {
                case JOptionPane.YES_OPTION:
                    saveDialogBox();
                    if (successfulSave == true) {
                        dispose();
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    dispose();
                    break;
                case JOptionPane.CANCEL_OPTION:
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    break;
                default:
                    break;
            }  
        }
    }   

    /**
     * Handles the action performed for the "exit" menu item.
     * Exits the program.
     * 
     * @param evt The action event triggered by the "exit" menu item.
     */
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        exitProgram();
    }    

    /**
     * Handles the action performed for the "undo" menu item.
     * Undos the last undone action if undo is available in the undo manager.
     *
     * @throws Exception if the undoManager encounters an error while trying to undo.
     * 
     * @param evt The action event triggered by the "undo" menu item.
     */
    private void undoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        if(undoManager.canUndo()) {
            try {
                undoManager.undo();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Caught Exception. Try again.", "Error",JOptionPane.ERROR_MESSAGE);  
            }
        }
    }   

    /**
     * Handles the action performed for the "redo" menu item. 
     * Redoes the last undone action if redo is available in the undo manager.
     *
     * @param evt The action event triggered by the "redo" menu item.
     */
    private void redoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        if(undoManager.canRedo()) {
            try {
                undoManager.redo();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Caught Exception. Try again.", "Error",JOptionPane.ERROR_MESSAGE);  
            }
        }
    }   

    /**
     * Handles the action performed for the "find" menu item. 
     * Prompts the user to enter text to find within the text area and highlights all instances of the text if found.
     * 
     * @param evt The action event triggered by the "find" menu item.
     */
    private void findMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        String searchText = JOptionPane.showInputDialog(this, "Enter text to find:", "Find Text", JOptionPane.PLAIN_MESSAGE);
        if (searchText != null && !searchText.isEmpty()) {
            String text = textArea.getText();
            int index = text.indexOf(searchText);
            if (index != -1) {
                Highlighter highlighter = textArea.getHighlighter();
                highlighter.removeAllHighlights();
                int count = 0;
                while (index >= 0) {
                    try {
                        highlighter.addHighlight(index, index + searchText.length(), DefaultHighlighter.DefaultPainter);
                        count++;
                        index = text.indexOf(searchText, index + searchText.length());
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
                findSuccessfulHighlightDeletion = false;
                JOptionPane.showMessageDialog(this, "Found " + count + " instances of '" + searchText + "'", "Found" , JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,"'" + searchText + "'" + " not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        textArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (findSuccessfulHighlightDeletion == false) {
                    Highlighter highlighter = textArea.getHighlighter();
                    highlighter.removeAllHighlights();
                    findSuccessfulHighlightDeletion = true;
                }
            }
        });
        textArea.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (findSuccessfulHighlightDeletion == false) {
                    Highlighter highlighter = textArea.getHighlighter();
                    highlighter.removeAllHighlights();
                    findSuccessfulHighlightDeletion = true;
                }
            }
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        });  
    }                               

    /**
    * Handles the action performed for the the "Replace" menu item.
    * This method prompts the user to enter the text to find and the text to replace it with.
    * It highlights all occurrences of the search text and replaces them with the replacement text.
    * After replacement, it re-highlights the newly replaced text and displays a message with the number of replacements made.
    * If the search text is not found, it displays a message indicating so.
    * It also ensures that all highlights are removed upon any mouse click or key press after a successful replacement.
    *
    * @param evt The action event triggered by the "Replace" menu item.
    */
    private void replaceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                
        String searchText = JOptionPane.showInputDialog(this, "Enter text to find:", "Find Text to Replace", JOptionPane.PLAIN_MESSAGE);
        if (searchText != null && !searchText.isEmpty()) {
            String text = textArea.getText();
            int index = text.indexOf(searchText);
            if (index != -1) {
                Highlighter highlighter = textArea.getHighlighter();
                highlighter.removeAllHighlights();
                int count = 0;
                while (index >= 0) {
                    try {
                        highlighter.addHighlight(index, index + searchText.length(), DefaultHighlighter.DefaultPainter);
                        count++;
                        index = text.indexOf(searchText, index + searchText.length());
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
                String replacementText = JOptionPane.showInputDialog(this, "Enter replacement text:", "Replace Text", JOptionPane.PLAIN_MESSAGE);
                if (replacementText != null && !replacementText.isEmpty()) {
                    text = text.replaceAll(searchText, replacementText);
                    textArea.setText(text);
                    highlighter.removeAllHighlights();
                    index = text.indexOf(replacementText);
                    while (index >= 0) {
                        try {
                            highlighter.addHighlight(index, index + replacementText.length(), DefaultHighlighter.DefaultPainter);
                            index = text.indexOf(replacementText, index + replacementText.length());
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                    }
                    int NumReplaced = text.split(replacementText, -1).length - 1;
                    replaceSuccessfulHighlightDeletion = false;
                    JOptionPane.showMessageDialog(this, "Replaced " + NumReplaced + " instances of '" + searchText + "' with '" + replacementText + "'", "Replaced", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "'" + searchText + "'" + " not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        textArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (replaceSuccessfulHighlightDeletion == false) {
                    Highlighter highlighter = textArea.getHighlighter();
                    highlighter.removeAllHighlights();
                    replaceSuccessfulHighlightDeletion = true;
                }
            }
        });
        textArea.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (replaceSuccessfulHighlightDeletion == false) {
                    Highlighter highlighter = textArea.getHighlighter();
                    highlighter.removeAllHighlights();
                    replaceSuccessfulHighlightDeletion = true;
                }
            }
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        });  
    }                                               

    /**
     * Handles the action performed for the "cut" menu item.
     * Copies the selected text in the text area to the system clipboard and deletes the text selected.
     *
     * @param evt The action event triggered by the "cut" menu item.
     */
    private void cutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                            
        textArea.cut();
    }                                           

    /**
     * Handles the action performed for the "copy" menu item.
     * Copies the selected text in the text area to the system clipboard.
     *
     * @param evt The action event triggered by the "copy" menu item.
     */
    private void copyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        textArea.copy();

    }                     

    /**
     * Handles the action performed for the "paste" menu item.
     * This method retrieves the contents from the system clipboard and inserts them into the text area at the current caret position or over the selcted text.
     * 
     * @throws UnsupportedFlavorException if the data flavor of the clipboard contents is not supported.
     * @throws IOException if an I/O error occurs while closing the output stream.
     * 
     * @param evt The action event triggered by the "paste" menu item.
     */
    private void pasteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                              
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(this);
        try {
             String pasteString = (String) contents.getTransferData(DataFlavor.stringFlavor);
            int start = textArea.getSelectionStart();
            int end = textArea.getSelectionEnd();
            if (start != end) { 
                textArea.replaceRange(pasteString, start, end);
            } else { 
                textArea.insert(pasteString, textArea.getCaretPosition());
            }
        } catch (UnsupportedFlavorException ex) {
            JOptionPane.showMessageDialog(this, "Caught Unsupported Flavor Exception. Try again.", "Error",JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Caught IOException when closing output stream. Try again.", "Error",JOptionPane.ERROR_MESSAGE);  
        }
    } 

    /**
     * Handles the action performed for the "delete" menu item.
     * Deletes the selected text in the text area.
     *
     * @param evt The action event triggered by the "delete" menu item.
     */
    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                               
        textArea.replaceRange("", textArea.getSelectionStart(),textArea.getSelectionEnd());
    }   

    /**
     * Handles the action performed for the "Select All" menu item.
     * Selects all text in the associated text area.
     * 
     * @param evt The action event triggered by the "Select All" menu item.
     */
    private void selectAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        textArea.selectAll();
    } 

    /**
     * Handles the action performed for the "Dark Mode" menu item.
     * Changes the background, caret color, and foreground color of a text area to simulate dark mode or revert back to the light mode.
     *
     * @param evt The action event triggered by the "Dark Mode" menu item.
     */
    private void darkModeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        final Color colorWhite = new Color(255, 255, 255);
        final Color colorGray = new Color(51, 51, 51);
        final Color colorDarkGray = new Color(32, 34, 37);
        
        if (darkMode == false) {
            textArea.setBackground(colorDarkGray);
            textArea.setCaretColor(colorWhite);
            textArea.setForeground(colorWhite);
            darkMode = true;
        } else if (darkMode == true) {
            textArea.setBackground(colorWhite);
            textArea.setCaretColor(colorGray);
            textArea.setForeground(colorGray);
            darkMode = false;
        }
    }                                                

    /**
     * Enables or disables word wrapping for the text area based on the state of the wordWrapMenuItem. 
     * If wordWrapMenuItem is selected, word wrapping is enabled; otherwise, it is disabled. 
     */
    private void wordWrap() {
        boolean wordWrapEnabled = wordWrapMenuItem.isSelected();
        textArea.setLineWrap(wordWrapEnabled);
        textArea.setWrapStyleWord(wordWrapEnabled);
    }    

    /**
     * Handles the action performed for the "redo" menu item. 
     * Changes the text area to have wrapped text if enabled or not have wrapped text if disabled.
     *
     * @param evt The action event triggered by the "redo" menu item.
     */
    private void wordWrapMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        wordWrap();
    }   

    /**
     * Handles the action performed for the "Font" menu item.
     * Displays a dialog that allows the user to select a font name, style, and size, and applies the selected font to a text area.
     *
     * @param evt The action event triggered by the "Font" menu item.
     */
    private void fontMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        Font currentFont = textArea.getFont();
        String currentFontName = currentFont.getFontName();
        int currentFontSize = currentFont.getSize();

        JComboBox<String> fontComboBox = new JComboBox<>(fontNames);
        fontComboBox.setSelectedItem(currentFontName);
        fontComboBox.setRenderer(new FontListCellRenderer());

        String[] fontStyles = {"Plain", "Bold", "Italic", "Bold Italic"};
        JComboBox<String> styleComboBox = new JComboBox<>(fontStyles);
        styleComboBox.setRenderer(new FontStyleCellRenderer());
        
        final int minFontSize = 8;
        final int maxFontSize = 72;
        final int stepFontSizeIncrement = 1;
        SpinnerNumberModel fontSizeModel = new SpinnerNumberModel(currentFontSize, minFontSize, maxFontSize, stepFontSizeIncrement); 
        JSpinner fontSizeSpinner = new JSpinner(fontSizeModel);

        JPanel fontPanel = new JPanel();
        fontPanel.add(new JLabel("Font:"));
        fontPanel.add(fontComboBox);
        fontPanel.add(new JLabel("Style:"));
        fontPanel.add(styleComboBox);
        fontPanel.add(new JLabel("Size:"));
        fontPanel.add(fontSizeSpinner);
    
        int result = JOptionPane.showConfirmDialog(this, fontPanel, "Select Font", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String selectedFontName = (String) fontComboBox.getSelectedItem();
            int selectedFontSize = (int) fontSizeSpinner.getValue();
            int selectedFontStyleIndex = styleComboBox.getSelectedIndex();
            
            int fontStyle;
            switch (selectedFontStyleIndex) {
                case 1: 
                    fontStyle = Font.BOLD;
                    break;
                case 2: 
                    fontStyle = Font.ITALIC;
                    break;
                case 3:
                    fontStyle = Font.BOLD | Font.ITALIC;
                    break;
                default:
                    fontStyle = Font.PLAIN;
                    break;
            }
            Font selectedFont = new Font(selectedFontName, fontStyle, selectedFontSize);
            textArea.setFont(selectedFont);
        }   
    }                                            

     /**
     * A custom list cell renderer that displays each cell with a specified font name.
     * This renderer sets the font of each cell to the font represented by the cell's text.
     */
    private class FontListCellRenderer extends DefaultListCellRenderer {
        /**
         * Returns a component configured to display the specified value.
         *
         * @param list the JList we're painting.
         * @param name the value returned by
         * list.getModel().getElementAt(index).
         * @param index the cell's index.
         * @param isSelected true if the specified cell is selected.
         * @param cellHasFocus true if the specified cell has the focus.
         * @return a component whose paint() method will render the specified value.
         */
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object name, int index, boolean isSelected, boolean cellHasFocus) {
            int fontSize = 12;
            JLabel fontListLabel = (JLabel) super.getListCellRendererComponent(list, name, index, isSelected, cellHasFocus);
            if (name instanceof String) {
                String fontName = (String) name;
                Font font = new Font(fontName, Font.PLAIN, fontSize); 
                fontListLabel.setFont(font);
                fontListLabel.setText(fontName);
            }
            return fontListLabel;
        }
    }
    
    /**
     * A custom cell renderer for displaying font styles in a JList. 
     * This renderer sets the font of each cell to match the style specified by the cell's value.
     */
    private class FontStyleCellRenderer extends DefaultListCellRenderer {
        /**
         * Returns a component that has been configured to display the specified value.
         * This method is called each time a cell is rendered in the JList.
         *
         * @param list the JList we're painting.
         * @param style the value to assign to the cell at {@code index}.
         * @param index the cell's index.
         * @param isSelected true if the specified cell is selected.
         * @param cellHasFocus true if the specified cell has the focus.
         * @return a component whose paint() method will render the specified value.
         */
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object style, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel FontStylelabel = (JLabel) super.getListCellRendererComponent(list, style, index, isSelected, cellHasFocus);
            if (style instanceof String) {
                String styleName = (String) style;
                int fontStyle;
                switch (styleName) {
                    case "Bold": 
                        fontStyle = Font.BOLD;
                        break;
                    case "Italic": 
                        fontStyle = Font.ITALIC;
                        break;
                    case "Bold Italic":
                        fontStyle = Font.BOLD | Font.ITALIC;
                        break;
                    default:
                        fontStyle = Font.PLAIN;
                        break;
                }
                Font font = new Font("Arial", fontStyle, 12);
                FontStylelabel.setFont(font);
                FontStylelabel.setText(styleName);
            }
            return FontStylelabel;  
        }
    }

    private String fileLocation; 
    private boolean successfulSave = false;
    private boolean darkMode = false;
    private boolean replaceSuccessfulHighlightDeletion = true;
    private boolean findSuccessfulHighlightDeletion = true;
    private final UndoManager undoManager;
    private JPopupMenu popupMenu;                  
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JCheckBoxMenuItem darkModeMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenuBar;
    private javax.swing.JPopupMenu.Separator editMenuSeparator1;
    private javax.swing.JPopupMenu.Separator editMenuSeparator2;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenuBar;
    private javax.swing.JPopupMenu.Separator fileMenuSeparator1;
    private javax.swing.JMenuItem findMenuItem;
    private javax.swing.JMenuItem fontMenuItem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newFileMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem redoMenuItem;
    private javax.swing.JMenuItem replaceMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem selectAllMenuItem;
    private javax.swing.JTextArea textArea;
    private javax.swing.JMenuItem undoMenuItem;
    private javax.swing.JMenu viewMenuBar;
    private javax.swing.JCheckBoxMenuItem wordWrapMenuItem;             
}