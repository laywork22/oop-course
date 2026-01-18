package gestionesami;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainApp extends Application {

    private List<Question> questionPool;
    private int currentIndex = 0;
    private int score = 0;

    // Elementi UI
    private Label lblProgress;
    private Label lblQuestion;
    private VBox optionsContainer;
    private Label lblFeedback;
    private Button btnAction; // Funge da "Conferma" o "Prossima"
    private ProgressBar progressBar;

    private boolean isAnswerChecked = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(){

    }

    @Override
    public void start(Stage primaryStage) {
        // 1. Inizializza il database delle domande dai PDF forniti
        initQuestions();

        // 2. Setup Layout Principale
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // --- TOP: Header e Progress ---
        VBox topBox = new VBox(10);
        Label title = new Label("Simulazione Teoria Java OOP & JavaFX");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        lblProgress = new Label("Domanda 1 di " + questionPool.size());
        progressBar = new ProgressBar(0);
        progressBar.setMaxWidth(Double.MAX_VALUE);

        topBox.getChildren().addAll(title, progressBar, lblProgress);
        topBox.setAlignment(Pos.CENTER);
        root.setTop(topBox);

        // --- CENTER: Domanda e Opzioni ---
        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(20, 0, 20, 0));
        centerBox.setAlignment(Pos.CENTER_LEFT);

        lblQuestion = new Label();
        lblQuestion.setWrapText(true);
        lblQuestion.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));

        optionsContainer = new VBox(10);

        lblFeedback = new Label();
        lblFeedback.setWrapText(true);
        lblFeedback.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblFeedback.setVisible(false);

        centerBox.getChildren().addAll(lblQuestion, optionsContainer, lblFeedback);

        // ScrollPane per schermi piccoli se la spiegazione è lunga
        ScrollPane scrollPane = new ScrollPane(centerBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent;");
        root.setCenter(scrollPane);

        // --- BOTTOM: Pulsanti ---
        HBox bottomBox = new HBox(15);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);

        btnAction = new Button("Conferma Risposta");
        btnAction.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAction.setPrefWidth(150);
        btnAction.setOnAction(e -> handleAction());

        bottomBox.getChildren().add(btnAction);
        root.setBottom(bottomBox);

        // 3. Mostra la prima domanda
        showQuestion();

        // 4. Scena
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setTitle("Simulatore OOP - Prova Finale");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleAction() {
        if (!isAnswerChecked) {
            checkAnswer();
        } else {
            nextQuestion();
        }
    }

    private void checkAnswer() {
        RadioButton selected = (RadioButton) optionsContainer.getChildren().stream()
                .filter(n -> n instanceof RadioButton && ((RadioButton) n).isSelected())
                .findFirst()
                .orElse(null);

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleziona una risposta per procedere!");
            alert.showAndWait();
            return;
        }

        Question currentQ = questionPool.get(currentIndex);
        boolean correct = selected.getText().equals(currentQ.correctAnswer);

        // Feedback Visivo
        optionsContainer.getChildren().forEach(node -> {
            RadioButton rb = (RadioButton) node;
            rb.setDisable(true); // Blocca modifiche
            if (rb.getText().equals(currentQ.correctAnswer)) {
                rb.setStyle("-fx-text-fill: green; -fx-font-weight: bold;"); // Evidenzia la corretta
            } else if (rb.isSelected() && !correct) {
                rb.setStyle("-fx-text-fill: red;"); // Evidenzia errore
            }
        });

        if (correct) {
            score++;
            lblFeedback.setText("Corretto! " + currentQ.explanation);
            lblFeedback.setTextFill(Color.GREEN);
        } else {
            lblFeedback.setText("Errato. " + currentQ.explanation);
            lblFeedback.setTextFill(Color.RED);
        }

        lblFeedback.setVisible(true);
        btnAction.setText("Prossima Domanda");
        isAnswerChecked = true;
    }

    private void nextQuestion() {
        currentIndex++;
        if (currentIndex < questionPool.size()) {
            showQuestion();
        } else {
            showSummary();
        }
    }

    private void showQuestion() {
        Question q = questionPool.get(currentIndex);

        lblQuestion.setText((currentIndex + 1) + ". " + q.text);
        lblFeedback.setVisible(false);
        isAnswerChecked = false;
        btnAction.setText("Conferma Risposta");

        // Aggiorna Progress
        lblProgress.setText("Domanda " + (currentIndex + 1) + " di " + questionPool.size());
        progressBar.setProgress((double) currentIndex / questionPool.size());

        // Setup Opzioni
        optionsContainer.getChildren().clear();
        ToggleGroup group = new ToggleGroup();

        List<String> shuffledOptions = new ArrayList<>(q.options);
        Collections.shuffle(shuffledOptions);

        for (String option : shuffledOptions) {
            RadioButton rb = new RadioButton(option);
            rb.setToggleGroup(group);
            rb.setWrapText(true);
            optionsContainer.getChildren().add(rb);
        }
    }

    private void showSummary() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Risultato Finale");
        alert.setHeaderText("Simulazione Terminata");

        double percentage = (double) score / questionPool.size() * 100;
        String evaluation = percentage >= 60 ? "Superato!" : "Non superato. Ripassa la teoria.";

        alert.setContentText("Punteggio: " + score + "/" + questionPool.size() + "\n" +
                "Percentuale: " + String.format("%.2f", percentage) + "%\n\n" +
                evaluation);

        alert.showAndWait();
        Platform.exit();
    }

    // --- DATABASE DOMANDE (Basato sui tuoi PDF) ---
    private void initQuestions() {
        questionPool = new ArrayList<>();

        // DOMANDE DA: Simulazione_ProvaTeorica + Nested Classes
        questionPool.add(new Question(
                "Per istanziare una Inner Class (non statica) 'Interna' dentro 'Esterna', trovandosi nel main, quale sintassi è corretta?",
                "new Esterna().new Interna()",
                "new Esterna.Interna()", "Esterna.new Interna()", "new Interna(new Esterna())",
                "Le Inner Class richiedono un'istanza della classe contenitore per esistere."
        ));

        questionPool.add(new Question(
                "Quale affermazione sulle Classi Anonime è vera?",
                "Non possono avere costruttori espliciti.",
                "Possono essere statiche.", "Possono implementare più interfacce contemporaneamente.", "Hanno sempre un nome generato casualmente.",
                "Le classi anonime non hanno nome, quindi non possono dichiarare costruttori (si usano gli init blocks)."
        ));

        // DOMANDE DA: Java_Thread + Concorrenza
        questionPool.add(new Question(
                "Perché la chiamata a wait() va racchiusa in un ciclo while e non in un if?",
                "Per gestire i 'risvegli spuri' (spurious wakeups).",
                "Perché il compilatore Java lo richiede.", "Per evitare il Deadlock.", "Per garantire la priorità del thread.",
                "Un thread potrebbe svegliarsi senza notifica o la condizione potrebbe cambiare subito dopo il risveglio."
        ));

        questionPool.add(new Question(
                "Se due thread chiamano metodi synchronized statici diversi della stessa classe, si bloccano a vicenda?",
                "Sì, perché il lock è sull'oggetto Class (monitor unico per la classe).",
                "No, perché sono metodi diversi.", "No, a meno che non usino le stesse variabili.", "Sì, ma solo se sono Daemon thread.",
                "I metodi statici synchronized acquisiscono il lock sull'oggetto Class, che è unico per tutta l'applicazione."
        ));

        // DOMANDE DA: JavaFX (Lifecycle & Binding) + Seconda Prova 2024
        questionPool.add(new Question(
                "Qual è la sequenza corretta del ciclo di vita di un'applicazione JavaFX?",
                "init() -> start() -> stop()",
                "main() -> start() -> init()", "launch() -> stop() -> start()", "start() -> init() -> stop()",
                "Il metodo init() viene chiamato prima dello start() (che avvia il Thread Grafico), e stop() alla chiusura."
        ));

        questionPool.add(new Question(
                "In JavaFX, cosa succede con un Binding Unidirezionale (bind)?",
                "Se la source cambia, la target si aggiorna. Se la target prova a cambiare, dà errore.",
                "Le due proprietà sono sempre sincronizzate in entrambi i sensi.", "La source si aggiorna se la target cambia.", "Viene creato un nuovo Thread per osservare.",
                "Il binding unidirezionale rende la proprietà target dipendente dalla source. La target non può essere settata manualmente mentre è 'bound'."
        ));

        // DOMANDE DA: Collections & Maps
        questionPool.add(new Question(
                "In una HashMap, cosa accade se due chiavi restituiscono lo stesso hashCode?",
                "Collisione: vengono gestite tramite lista o albero nello stesso bucket.",
                "Il nuovo valore sovrascrive il vecchio immediatamente.", "Viene lanciata una eccezione a runtime.", "La mappa si ridimensiona automaticamente.",
                "Le collisioni vengono risolte concatenando gli elementi (chaining) nello stesso bucket."
        ));

        questionPool.add(new Question(
                "Quale Map mantiene l'ordine di inserimento delle chiavi?",
                "LinkedHashMap",
                "TreeMap", "HashMap", "Hashtable",
                "LinkedHashMap usa una lista doppiamente concatenata interna per preservare l'ordine di inserimento."
        ));

        // DOMANDE DA: I/O & Decorator
        questionPool.add(new Question(
                "Nel pattern Decorator (Java I/O), qual è il ruolo di FileInputStream?",
                "Concrete Component (Node Stream)",
                "Decorator", "Abstract Component", "Strategy",
                "FileInputStream si connette alla risorsa fisica (file), quindi è il componente concreto che viene poi decorato (es. da BufferedInputStream)."
        ));

        // DOMANDE DA: Seconda Prova Intercorso 2024 (Specifica)
        questionPool.add(new Question(
                "Dato: 'BooleanBinding ex = Bindings.or(A, B)'. Quando 'ex' è true?",
                "Quando almeno uno tra A o B è true.",
                "Solo quando sia A che B sono true.", "Solo quando A è false e B è true.", "Mai, Bindings.or non esiste.",
                "Bindings.or crea una condizione logica OR tra due ObservableBooleanValue."
        ));

        Collections.shuffle(questionPool); // Mescola le domande
    }

    // --- CLASSE DI SUPPORTO ---
    class Question {
        String text;
        String correctAnswer;
        List<String> options;
        String explanation;

        public Question(String text, String correct, String w1, String w2, String w3, String explanation) {
            this.text = text;
            this.correctAnswer = correct;
            this.options = new ArrayList<>();
            this.options.add(correct);
            this.options.add(w1);
            this.options.add(w2);
            this.options.add(w3);
            this.explanation = explanation;
        }
    }
}