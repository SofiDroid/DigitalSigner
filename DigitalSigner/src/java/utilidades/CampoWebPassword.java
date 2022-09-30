package utilidades;

/**
 *
 * @author ihuegal
 */
public class CampoWebPassword extends CampoWeb {
    
    protected boolean feedback = false;
    protected String promptLabel = "Escriba su contraseña";
    protected String weakLabel = "Débil";
    protected String goodLabel = "Buena";
    protected String strongLabel = "Fuerte";
    protected boolean toggleMask = false;
    protected boolean redisplay = true;
    
    public CampoWebPassword() {
        super(Tipo.Password);
        super.setMaxlength("50");
    }

    public void setValue(String value) {
        super.setValue(value);
    }

    @Override
    public String getValue() {
        return (String)super.getValue();
    }

    public boolean isFeedback() {
        return feedback;
    }

    public void setFeedback(boolean feedback) {
        this.feedback = feedback;
    }

    public String getPromptLabel() {
        return promptLabel;
    }

    public void setPromptLabel(String promptLabel) {
        this.promptLabel = promptLabel;
    }

    public String getWeakLabel() {
        return weakLabel;
    }

    public void setWeakLabel(String weakLabel) {
        this.weakLabel = weakLabel;
    }

    public String getGoodLabel() {
        return goodLabel;
    }

    public void setGoodLabel(String goodLabel) {
        this.goodLabel = goodLabel;
    }

    public String getStrongLabel() {
        return strongLabel;
    }

    public void setStrongLabel(String strongLabel) {
        this.strongLabel = strongLabel;
    }

    public boolean isToggleMask() {
        return toggleMask;
    }

    public void setToggleMask(boolean toggleMask) {
        this.toggleMask = toggleMask;
    }

    public boolean isRedisplay() {
        return redisplay;
    }

    public void setRedisplay(boolean redisplay) {
        this.redisplay = redisplay;
    }
}
