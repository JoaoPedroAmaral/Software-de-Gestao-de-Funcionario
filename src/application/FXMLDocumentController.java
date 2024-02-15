package application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class FXMLDocumentController implements Initializable {

	@FXML
	private Button close;

	@FXML
	private TextField login;

	@FXML
	private PasswordField senha;

	@FXML
	private Button submit;

	@FXML
	private Text alert;

	@FXML
	private Hyperlink cadastrese;

	private double x = 0;
	private double y = 0;
	
	private String urlFoto;
	
	File inputFile = new File("usuarios.txt");
	File tempFile = new File("temp.txt");

	public void enter() {
		senha.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					login();
				}
			}
		});

		login.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					login();
				}
			}
		});
	}

	public void cadastrese() {
		submit.getScene().getWindow().hide();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Cadastro.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root, 600, 550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			stage.setTitle("PsiMind");
			stage.getIcons().add(new Image(getClass().getResourceAsStream("../img/icone.png")));

			root.setOnMousePressed((MouseEvent event) -> {
				x = event.getSceneX();
				y = event.getSceneY();
			});

			root.setOnMouseDragged((MouseEvent event) -> {
				stage.setX(event.getScreenX() - x);
				stage.setY(event.getScreenY() - y);
			});

			root.setOnMouseDragged((MouseEvent event) -> {
				stage.setX(event.getScreenX() - x);
				stage.setY(event.getScreenY() - y);

				stage.setOpacity(.8);
			});

			root.setOnMouseReleased((MouseEvent event) -> {
				stage.setOpacity(1);
			});

			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String foto() {
		 try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
			 String word;
            while ((word = reader.readLine()) != null) {
                String[] linha = word.split("^");
                for (int i = 0; i <= linha.length - 1; i++) {
                    String[] partes = linha[i].split(",");
                    if(partes[1].equals(login.getText()) && partes[2].equals(senha.getText())) {
                     	 urlFoto = partes[3].toString();
                     	 System.out.println(partes[3]);
                      }
                }
            }
            return urlFoto;
		 } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "/img/profile.jpg";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "/img/profile.jpg";
		}
	}
	
	public boolean temFoto() {
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
               String word;
               boolean verificar = true;         
               while ((word = reader.readLine()) != null) {
                   String[] linha = word.split("^");
                   for (int i = 0; i <= linha.length - 1; i++) {
                       String[] partes = linha[i].split(",");
                       if(partes[3].equals("/img/profile.jpg") && partes[1].equals(login.getText())) {
                    	   verificar = false;
                       }
                   }
               }
               return verificar;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
	}

	public void logado() {
		if (login.getText().isEmpty() || senha.getText().isEmpty()) {
			alert.setText("Campos não prenchidos detectados! porfavor verifique se algum campo foi esquecido.");
		} else {
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){

                String word;
                while ((word = reader.readLine()) != null) {
                    String[] linha = word.split("^");
                    for (int i = 0; i <= linha.length - 1; i++) {
                        String[] partes = linha[i].split(",");
                        System.out.println(temFoto());
                        if (partes[1].equals(login.getText()) && partes[2].equals(senha.getText())) {
                           if(temFoto()) {
                        	   writer.write(partes[0] + "," + partes[1] + "," + partes[2] + "," + foto() + "," + "-"  + "," + "^");
                           }else {
                        	   writer.write(partes[0] + "," + partes[1] + "," + partes[2] + "," + "/img/profile.jpg" + "," + "-" + "," + "^");
                           }
                        } else {
                        	if(temFoto()) {
                         	   writer.write(partes[0] + "," + partes[1] + "," + partes[2] + "," + foto() + "," + "x" + ","+ "^");
                            }else {
                         	   writer.write(partes[0] + "," + partes[1] + "," + partes[2] + "," + "/img/profile.jpg" + "," + "x" + "," + "^");
                            }
                        }

                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                alert.setText("Erro! Falha ao realizar o login");
            }
            }
	}
	
	public void login() {
		try {
			if (login.getText().isEmpty() || senha.getText().isEmpty()) {
				alert.setText("Campos não prenchidos detectados! porfavor verifique se algum campo foi esquecido.");
			} else {
				File inputFile = new File("usuarios.txt");
				
				try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
					String word;
					while ((word = reader.readLine()) != null) {
						String[] linha = word.split("^");
						for (int i = 0; i <= linha.length - 1; i++) {
							String[] partes = linha[i].split(",");
							if (partes[1].equals(login.getText()) && partes[2].equals(senha.getText())) {
								logado();
								
								submit.getScene().getWindow().hide();
								Parent root = FXMLLoader.load(getClass().getResource("Software.fxml"));
								Stage stage = new Stage();
								Scene scene = new Scene(root, 1280, 700);

								stage.setFullScreen(true);

								stage.setTitle("PsiMind");
								stage.getIcons().add(new Image(getClass().getResourceAsStream("../img/icone.png")));

								stage.initStyle(StageStyle.TRANSPARENT);
								stage.setScene(scene);
								stage.show();
								
							}
						}
						alert.setText("Login e Senha incorretos ou não existentes");
					}

				} catch (IOException e) {
					alert.setText("Erro! falha ao realizar Login");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		System.exit(0);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}
}
