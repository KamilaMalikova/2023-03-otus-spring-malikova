package ru.otus.csv.csv.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.otus.csv.config.QuizProperties;
import ru.otus.csv.service.IoService;
import ru.otus.csv.service.SimpleQuestionsService;
import ru.otus.csv.dao.QuestionsDao;
import ru.otus.csv.model.Question;
import java.util.List;
import java.util.Locale;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SimpleQuestionsServiceTest {
  private static final String EMPTY_STRING = "";
  private static final String QUESTIONS = "quiz.questions";
  private static final String NAME = "quiz.name";
  private static final String RESULT = "quiz.result";
  private static final String ANSWER = "quiz.answer";
  private static final String USERNAME = "User";
  private static final int SELECTED_ANSWER = 0;
  @Mock
  private QuestionsDao questionsDao;
  @Mock
  private IoService ioService;
  @Mock
  private QuizProperties properties;
  @InjectMocks
  private SimpleQuestionsService questionsService;

  @Test
  void testQuiz() {
    List<Question> questions = List.of(
            new Question("In which Italian city can you find the Colosseum?", List.of("Rome", "Venice", "Naples", "Milan"), "Rome"),
            new Question("Who wrote Catch-22?", List.of("Joseph Heller", "Ernest Hemingway", "Charles Dickens", "Mark Twain"), "Joseph Heller")
    );

    int correctAnswers = 2;

    String[] expectedResponseArray = new String[]{USERNAME, String.valueOf(correctAnswers)};

    doReturn(questions).when(questionsDao).findQuestions(anyString(), any());
    doReturn(EMPTY_STRING).when(questionsDao).getMessage(anyString(), any());

    doNothing().when(ioService).writeLine(anyString());
    doNothing().when(ioService).writeQuestion(any());
    doNothing().when(ioService).write(anyString());

    doReturn(USERNAME).when(ioService).readString();
    doReturn(SELECTED_ANSWER).when(ioService).readInt();

    doReturn(Locale.ENGLISH).when(properties).getLocale();
    doReturn(RESULT).when(properties).getResultCode();
    doReturn(ANSWER).when(properties).getAnswerCode();
    doReturn(NAME).when(properties).getNameCode();
    doReturn(QUESTIONS).when(properties).getQuestionsCode();

    questionsService.quiz();

    verify(questionsDao, times(1))
            .getMessageFromTemplate(RESULT, expectedResponseArray, Locale.ENGLISH);
  }
}