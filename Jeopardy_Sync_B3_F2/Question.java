/**
 * A jeopardy question that knows its answer.
 * 
 * @author Frank Lai 
 * @version 2018-06-03 1.01
 */
public class Question
{
    // Instance.
    private String question;
    private String[] answer;
    private int correctAnswerIndex;

    /**
     * Creates a question with all params defined.
     * 
     * @param question the question
     * @param answers the answers to the question
     * @param correctAnswer the real answer to the question
     */
    public Question(String questionText, String[] answers, int correctAnswer)
    {
        if(question != null)
        {
            question = questionText;
        }
        else
        {
            question = "";   
        }
        if(answer != null)
        {
            answer = answers;
        } // end of if(answer != null)
        else
        {
            answer = new String[]{"NA"};
        }
        // Ensures no array out of bounds exception.
        if(correctAnswer < answer.length && correctAnswer >= 0)
        {
            correctAnswerIndex = correctAnswer;
        } // end of if(index < answer.length && index >= 0)
        else
        {
            correctAnswerIndex = 0;
        }
    }

    /* Mutators */
    /**
     * Sets the answer array.
     * 
     * @param answer all the answers
     */
    public void setAnswerAll(String[] newAnswer)
    {
        answer = newAnswer;
    } // end of method setAnswerAll(String[] newAnswer)

    /**
     * Sets an answer.
     * 
     * @param answer an answer
     * @param index the index of the answer
     */
    public void setAnswer(String newAnswer, int index)
    {
        // Ensures no array out of bounds exception.
        if(index < answer.length && index >= 0)
        {
            answer[index] = newAnswer;
        } // end of if(index < answer.length && index >= 0)
    } // end of method setAnswer()
    /**
     * Sets the correct answer.
     * 
     * @param index the index of the answer
     */
    public void setCorrectAnswer(int index)
    {
        // Ensures no array out of bounds exception.
        if(index < answer.length && index >= 0)
        {
            correctAnswerIndex = index;
        } // end of if(index < answer.length && index >= 0)
    } // end of method setCorrectAnswer()
    /**
     * Sets the question.
     * 
     * @param the new question
     */
    public void setQuestion(String questionText)
    {
        if(question != null)
        {
            question = questionText;
        }
    } // end of method setQuestion(String questionText)
    /* Accessors */
    /**
     * Gets the answer array.
     * 
     * @return all the answers
     */
    public String[] getAnswerAll()
    {
        return answer;
    } // end of method getAnswerAll()
    /**
     * Gets a specific answer.
     * 
     * @param index the index for the answer
     * @return a singular answer
     */
    public String getAnswer(int index)
    {
        // Ensures no array out of bounds exception.
        if(index < answer.length && index >= 0)
        {
            return answer[index];
        } 
        else
        {
            throw new IllegalArgumentException("Must be inbound of array!");
        } // end of if(index < answer.length && index >= 0)
    } // end of method getAnswer(int index)
    /**
     * Gets the correct answer index.
     * 
     * @return the correct answer index
     */
    public int getCorrectAnswerIndex()
    {
        return correctAnswerIndex;
    } // end of getCorrectAnswerIndex()
    /**
     * Gets the correct answer.
     * 
     * @return the correct answer
     */
    public String getCorrectAnswer()
    {
        if(correctAnswerIndex < answer.length && correctAnswerIndex >= 0)
        {
            return answer[correctAnswerIndex];
        } 
        else
        {
            throw new IllegalArgumentException("Must be inbound of array!");
        } // end of if(index < answer.length && index >= 0)
    } // end of getCorrectAnswer()
    
    /**
     * Gets the question.
     * 
     * @return the question
     */
    public String getQuestion()
    {
        return question;
    } // end of method getQuestion()
    
} // end of class Question
