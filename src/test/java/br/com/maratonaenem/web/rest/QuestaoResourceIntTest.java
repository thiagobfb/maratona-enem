package br.com.maratonaenem.web.rest;

import br.com.maratonaenem.MaratonaEnemApp;

import br.com.maratonaenem.domain.Questao;
import br.com.maratonaenem.repository.QuestaoRepository;
import br.com.maratonaenem.service.QuestaoService;
import br.com.maratonaenem.repository.search.QuestaoSearchRepository;
import br.com.maratonaenem.service.dto.QuestaoDTO;
import br.com.maratonaenem.service.mapper.QuestaoMapper;
import br.com.maratonaenem.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static br.com.maratonaenem.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuestaoResource REST controller.
 *
 * @see QuestaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MaratonaEnemApp.class)
public class QuestaoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private QuestaoMapper questaoMapper;

    @Autowired
    private QuestaoService questaoService;

    @Autowired
    private QuestaoSearchRepository questaoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restQuestaoMockMvc;

    private Questao questao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestaoResource questaoResource = new QuestaoResource(questaoService);
        this.restQuestaoMockMvc = MockMvcBuilders.standaloneSetup(questaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questao createEntity() {
        Questao questao = new Questao()
            .descricao(DEFAULT_DESCRICAO);
        return questao;
    }

    @Before
    public void initTest() {
        questaoRepository.deleteAll();
        questaoSearchRepository.deleteAll();
        questao = createEntity();
    }

    @Test
    public void createQuestao() throws Exception {
        int databaseSizeBeforeCreate = questaoRepository.findAll().size();

        // Create the Questao
        QuestaoDTO questaoDTO = questaoMapper.toDto(questao);
        restQuestaoMockMvc.perform(post("/api/questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Questao in the database
        List<Questao> questaoList = questaoRepository.findAll();
        assertThat(questaoList).hasSize(databaseSizeBeforeCreate + 1);
        Questao testQuestao = questaoList.get(questaoList.size() - 1);
        assertThat(testQuestao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the Questao in Elasticsearch
        Questao questaoEs = questaoSearchRepository.findOne(testQuestao.getId());
        assertThat(questaoEs).isEqualToIgnoringGivenFields(testQuestao);
    }

    @Test
    public void createQuestaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questaoRepository.findAll().size();

        // Create the Questao with an existing ID
        questao.setId("existing_id");
        QuestaoDTO questaoDTO = questaoMapper.toDto(questao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestaoMockMvc.perform(post("/api/questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questao in the database
        List<Questao> questaoList = questaoRepository.findAll();
        assertThat(questaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllQuestaos() throws Exception {
        // Initialize the database
        questaoRepository.save(questao);

        // Get all the questaoList
        restQuestaoMockMvc.perform(get("/api/questaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questao.getId())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    public void getQuestao() throws Exception {
        // Initialize the database
        questaoRepository.save(questao);

        // Get the questao
        restQuestaoMockMvc.perform(get("/api/questaos/{id}", questao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questao.getId()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    public void getNonExistingQuestao() throws Exception {
        // Get the questao
        restQuestaoMockMvc.perform(get("/api/questaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateQuestao() throws Exception {
        // Initialize the database
        questaoRepository.save(questao);
        questaoSearchRepository.save(questao);
        int databaseSizeBeforeUpdate = questaoRepository.findAll().size();

        // Update the questao
        Questao updatedQuestao = questaoRepository.findOne(questao.getId());
        updatedQuestao
            .descricao(UPDATED_DESCRICAO);
        QuestaoDTO questaoDTO = questaoMapper.toDto(updatedQuestao);

        restQuestaoMockMvc.perform(put("/api/questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questaoDTO)))
            .andExpect(status().isOk());

        // Validate the Questao in the database
        List<Questao> questaoList = questaoRepository.findAll();
        assertThat(questaoList).hasSize(databaseSizeBeforeUpdate);
        Questao testQuestao = questaoList.get(questaoList.size() - 1);
        assertThat(testQuestao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the Questao in Elasticsearch
        Questao questaoEs = questaoSearchRepository.findOne(testQuestao.getId());
        assertThat(questaoEs).isEqualToIgnoringGivenFields(testQuestao);
    }

    @Test
    public void updateNonExistingQuestao() throws Exception {
        int databaseSizeBeforeUpdate = questaoRepository.findAll().size();

        // Create the Questao
        QuestaoDTO questaoDTO = questaoMapper.toDto(questao);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestaoMockMvc.perform(put("/api/questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Questao in the database
        List<Questao> questaoList = questaoRepository.findAll();
        assertThat(questaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteQuestao() throws Exception {
        // Initialize the database
        questaoRepository.save(questao);
        questaoSearchRepository.save(questao);
        int databaseSizeBeforeDelete = questaoRepository.findAll().size();

        // Get the questao
        restQuestaoMockMvc.perform(delete("/api/questaos/{id}", questao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean questaoExistsInEs = questaoSearchRepository.exists(questao.getId());
        assertThat(questaoExistsInEs).isFalse();

        // Validate the database is empty
        List<Questao> questaoList = questaoRepository.findAll();
        assertThat(questaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchQuestao() throws Exception {
        // Initialize the database
        questaoRepository.save(questao);
        questaoSearchRepository.save(questao);

        // Search the questao
        restQuestaoMockMvc.perform(get("/api/_search/questaos?query=id:" + questao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questao.getId())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Questao.class);
        Questao questao1 = new Questao();
        questao1.setId("id1");
        Questao questao2 = new Questao();
        questao2.setId(questao1.getId());
        assertThat(questao1).isEqualTo(questao2);
        questao2.setId("id2");
        assertThat(questao1).isNotEqualTo(questao2);
        questao1.setId(null);
        assertThat(questao1).isNotEqualTo(questao2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestaoDTO.class);
        QuestaoDTO questaoDTO1 = new QuestaoDTO();
        questaoDTO1.setId("id1");
        QuestaoDTO questaoDTO2 = new QuestaoDTO();
        assertThat(questaoDTO1).isNotEqualTo(questaoDTO2);
        questaoDTO2.setId(questaoDTO1.getId());
        assertThat(questaoDTO1).isEqualTo(questaoDTO2);
        questaoDTO2.setId("id2");
        assertThat(questaoDTO1).isNotEqualTo(questaoDTO2);
        questaoDTO1.setId(null);
        assertThat(questaoDTO1).isNotEqualTo(questaoDTO2);
    }
}
