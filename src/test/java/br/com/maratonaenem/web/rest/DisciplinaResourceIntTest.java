package br.com.maratonaenem.web.rest;

import br.com.maratonaenem.MaratonaEnemApp;

import br.com.maratonaenem.domain.Disciplina;
import br.com.maratonaenem.repository.DisciplinaRepository;
import br.com.maratonaenem.service.DisciplinaService;
import br.com.maratonaenem.repository.search.DisciplinaSearchRepository;
import br.com.maratonaenem.service.dto.DisciplinaDTO;
import br.com.maratonaenem.service.mapper.DisciplinaMapper;
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

import br.com.maratonaenem.domain.enumeration.TipoDisciplina;
/**
 * Test class for the DisciplinaResource REST controller.
 *
 * @see DisciplinaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MaratonaEnemApp.class)
public class DisciplinaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final TipoDisciplina DEFAULT_TIPO_DISCIPLINA = TipoDisciplina.EXATAS;
    private static final TipoDisciplina UPDATED_TIPO_DISCIPLINA = TipoDisciplina.HUMANAS;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private DisciplinaMapper disciplinaMapper;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private DisciplinaSearchRepository disciplinaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDisciplinaMockMvc;

    private Disciplina disciplina;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisciplinaResource disciplinaResource = new DisciplinaResource(disciplinaService);
        this.restDisciplinaMockMvc = MockMvcBuilders.standaloneSetup(disciplinaResource)
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
    public static Disciplina createEntity() {
        Disciplina disciplina = new Disciplina()
            .nome(DEFAULT_NOME)
            .tipoDisciplina(DEFAULT_TIPO_DISCIPLINA);
        return disciplina;
    }

    @Before
    public void initTest() {
        disciplinaRepository.deleteAll();
        disciplinaSearchRepository.deleteAll();
        disciplina = createEntity();
    }

    @Test
    public void createDisciplina() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();

        // Create the Disciplina
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);
        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isCreated());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeCreate + 1);
        Disciplina testDisciplina = disciplinaList.get(disciplinaList.size() - 1);
        assertThat(testDisciplina.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDisciplina.getTipoDisciplina()).isEqualTo(DEFAULT_TIPO_DISCIPLINA);

        // Validate the Disciplina in Elasticsearch
        Disciplina disciplinaEs = disciplinaSearchRepository.findOne(testDisciplina.getId());
        assertThat(disciplinaEs).isEqualToIgnoringGivenFields(testDisciplina);
    }

    @Test
    public void createDisciplinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();

        // Create the Disciplina with an existing ID
        disciplina.setId("existing_id");
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllDisciplinas() throws Exception {
        // Initialize the database
        disciplinaRepository.save(disciplina);

        // Get all the disciplinaList
        restDisciplinaMockMvc.perform(get("/api/disciplinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplina.getId())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipoDisciplina").value(hasItem(DEFAULT_TIPO_DISCIPLINA.toString())));
    }

    @Test
    public void getDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.save(disciplina);

        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", disciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disciplina.getId()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.tipoDisciplina").value(DEFAULT_TIPO_DISCIPLINA.toString()));
    }

    @Test
    public void getNonExistingDisciplina() throws Exception {
        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.save(disciplina);
        disciplinaSearchRepository.save(disciplina);
        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Update the disciplina
        Disciplina updatedDisciplina = disciplinaRepository.findOne(disciplina.getId());
        updatedDisciplina
            .nome(UPDATED_NOME)
            .tipoDisciplina(UPDATED_TIPO_DISCIPLINA);
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(updatedDisciplina);

        restDisciplinaMockMvc.perform(put("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isOk());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeUpdate);
        Disciplina testDisciplina = disciplinaList.get(disciplinaList.size() - 1);
        assertThat(testDisciplina.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDisciplina.getTipoDisciplina()).isEqualTo(UPDATED_TIPO_DISCIPLINA);

        // Validate the Disciplina in Elasticsearch
        Disciplina disciplinaEs = disciplinaSearchRepository.findOne(testDisciplina.getId());
        assertThat(disciplinaEs).isEqualToIgnoringGivenFields(testDisciplina);
    }

    @Test
    public void updateNonExistingDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Create the Disciplina
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDisciplinaMockMvc.perform(put("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isCreated());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.save(disciplina);
        disciplinaSearchRepository.save(disciplina);
        int databaseSizeBeforeDelete = disciplinaRepository.findAll().size();

        // Get the disciplina
        restDisciplinaMockMvc.perform(delete("/api/disciplinas/{id}", disciplina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean disciplinaExistsInEs = disciplinaSearchRepository.exists(disciplina.getId());
        assertThat(disciplinaExistsInEs).isFalse();

        // Validate the database is empty
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.save(disciplina);
        disciplinaSearchRepository.save(disciplina);

        // Search the disciplina
        restDisciplinaMockMvc.perform(get("/api/_search/disciplinas?query=id:" + disciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplina.getId())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipoDisciplina").value(hasItem(DEFAULT_TIPO_DISCIPLINA.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disciplina.class);
        Disciplina disciplina1 = new Disciplina();
        disciplina1.setId("id1");
        Disciplina disciplina2 = new Disciplina();
        disciplina2.setId(disciplina1.getId());
        assertThat(disciplina1).isEqualTo(disciplina2);
        disciplina2.setId("id2");
        assertThat(disciplina1).isNotEqualTo(disciplina2);
        disciplina1.setId(null);
        assertThat(disciplina1).isNotEqualTo(disciplina2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisciplinaDTO.class);
        DisciplinaDTO disciplinaDTO1 = new DisciplinaDTO();
        disciplinaDTO1.setId("id1");
        DisciplinaDTO disciplinaDTO2 = new DisciplinaDTO();
        assertThat(disciplinaDTO1).isNotEqualTo(disciplinaDTO2);
        disciplinaDTO2.setId(disciplinaDTO1.getId());
        assertThat(disciplinaDTO1).isEqualTo(disciplinaDTO2);
        disciplinaDTO2.setId("id2");
        assertThat(disciplinaDTO1).isNotEqualTo(disciplinaDTO2);
        disciplinaDTO1.setId(null);
        assertThat(disciplinaDTO1).isNotEqualTo(disciplinaDTO2);
    }
}
