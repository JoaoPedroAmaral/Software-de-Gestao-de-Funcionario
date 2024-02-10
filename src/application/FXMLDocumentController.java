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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;

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
	 
	public void enter() {
        // Adiciona um EventHandler para capturar o evento de tecla pressionada no campo senha
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
	
	public void login() {
		try {
			login.getText();
			senha.getText();

			if (login.getText().isEmpty() || senha.getText().isEmpty()) {
				alert.setText("Campos não prenchidos detectados! porfavor verifique se algum campo foi esquecido.");
			} else {
				try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
					String word;
					while ((word = reader.readLine()) != null) {
						String[] linha= word.split(":");
						for(int i = 0; i <= linha.length-1; i++) {
							String[] partes = linha[i].split(",");
							if (partes[1].equals(login.getText()) && partes[2].equals(senha.getText())) {
								submit.getScene().getWindow().hide();
								Parent root = FXMLLoader.load(getClass().getResource("Software.fxml"));
								Stage stage = new Stage();
								 Rectangle2D screenBounds = Screen.getPrimary().getBounds();
								Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

								root.setOnMousePressed((MouseEvent event) -> {
									x = event.getSceneX();
									y = event.getSceneY();
								});

								root.setOnMouseDragged((MouseEvent event) -> {
									stage.setX(event.getScreenX() - x);
									stage.setY(event.getScreenY() - y);
								});

								stage.setTitle("PsiMind");
								stage.getIcons().add(new Image(getClass().getResourceAsStream("../img/icone.png")));
								
								stage.initStyle(StageStyle.TRANSPARENT);
								stage.setScene(scene);
								stage.show();

							} else {
								alert.setText("Login e Senha incorretos ou não existentes");
							}
						}
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
