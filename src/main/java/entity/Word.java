package entity;

public class Word {
    private long id;
    private final String value;
    private final String translate;
    private final String transcription;
    private int attempts;

    private int maxWrongCount;

    public Word(String value, String translate, String transcription) {
        this.value = value;
        this.translate = translate;
        this.transcription = transcription;
        attempts = 0;
        maxWrongCount = 0;
    }

    public String getValue() {
        return value;
    }

    public String getTranslate() {
        return translate;
    }

    public String getTranscription() {
        return transcription;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMaxWrongCount() {
        return maxWrongCount;
    }

    public void setMaxWrongCount(int maxWrongCount) {
        this.maxWrongCount = maxWrongCount;
    }

    @Override
    public String toString() {
        return "Word{" +
                "value='" + value + '\'' +
                ", translate='" + translate + '\'' +
                ", transcription='" + transcription + '\'' +
                '}';
    }
}
