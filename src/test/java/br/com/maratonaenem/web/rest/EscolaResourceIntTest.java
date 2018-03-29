package br.com.maratonaenem.web.rest;

import br.com.maratonaenem.MaratonaEnemApp;

import br.com.maratonaenem.domain.Escola;
import br.com.maratonaenem.repository.EscolaRepository;
import br.com.maratonaenem.service.EscolaService;
import br.com.maratonaenem.repository.search.EscolaSearchRepository;
import br.com.maratonaenem.service.dto.EscolaDTO;
import br.com.maratonaenem.service.mapper.EscolaMapper;
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

import br.com.maratonaenem.domain.enumeration.TipoEscola;
/**
 * Test class for the EscolaResource REST controller.
 *
 * @see EscolaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MaratonaEnemApp.class)
public class EscolaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final TipoEscola DEFAULT_TIPO_ESCOLA = TipoEscola.PUBLICA;
    private static final TipoEscola UPDATED_TIPO_ESCOLA = TipoEscola.PARTICULAR;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private EscolaMapper escolaMapper;

    @Autowired
    private EscolaService escolaService;

    @Autowired
    private EscolaSearchRepository escolaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEscolaMockMvc;

    private Escola escola;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EscolaResource escolaResource = new EscolaResource(escolaService);
        this.restEscolaMockMvc = MockMvcBuilders.standaloneSetup(escolaResource)
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
    public static Escola createEntity() {
        Escola escola = new Escola()
            .nome(DEFAULT_NOME)
            .tipoEscola(DEFAULT_TIPO_ESCOLA);
        return escola;
    }

    @Before
    public void initTest() {
        escolaRepository.deleteAll();
        escolaSearchRepository.deleteAll();
        escola = createEntity();
    }

    @Test
    public void createEscola() throws Exception {
        int databaseSizeBeforeCreate = escolaRepository.findAll().size();

        // Create the Escola
        EscolaDTO escolaDTO = escolaMapper.toDto(escola);
        restEscolaMockMvc.perform(post("/api/escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaDTO)))
            .andExpect(status().isCreated());

        // Validate the Escola in the database
        List<Escola> escolaList = escolaRepository.findAll();
        assertThat(escolaList).hasSize(databaseSizeBeforeCreate + 1);
        Escola testEscola = escolaList.get(escolaList.size() - 1);
        assertThat(testEscola.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEscola.getTipoEscola()).isEqualTo(DEFAULT_TIPO_ESCOLA);

        // Validate the Escola in Elasticsearch
        Escola escolaEs = escolaSearchRepository.findOne(testEscola.getId());
        assertThat(escolaEs).isEqualToIgnoringGivenFields(testEscola);
    }

    @Test
    public void createEscolaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = escolaRepository.findAll().size();

        // Create the Escola with an existing ID
        escola.setId("existing_id");
        EscolaDTO escolaDTO = escolaMapper.toDto(escola);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscolaMockMvc.perform(post("/api/escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Escola in the database
        List<Escola> escolaList = escolaRepository.findAll();
        assertThat(escolaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllEscolas() throws Exception {
        // Initialize the database
        escolaRepository.save(escola);

        // Get all the escolaList
        restEscolaMockMvc.perform(get("/api/escolas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escola.getId())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipoEscola").value(hasItem(DEFAULT_TIPO_ESCOLA.toString())));
    }

    @Test
    public void getEscola() throws Exception {
        // Initialize the database
        escolaRepository.save(escola);

        // Get the escola
        restEscolaMockMvc.perform(get("/api/escolas/{id}", escola.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(escola.getId()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.tipoEscola").value(DEFAULT_TIPO_ESCOLA.toString()));
    }

    @Test
    public void getNonExistingEscola() throws Exception {
        // Get the escola
        restEscolaMockMvc.perform(get("/api/escolas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEscola() throws Exception {
        // Initialize the database
        escolaRepository.save(escola);
        escolaSearchRepository.save(escola);
        int databaseSizeBeforeUpdate = escolaRepository.findAll().size();

        // Update the escola
        Escola updatedEscola = escolaRepository.findOne(escola.getId());
        updatedEscola
            .nome(UPDATED_NOME)
            .tipoEscola(UPDATED_TIPO_ESCOLA);
        EscolaDTO escolaDTO = escolaMapper.toDto(updatedEscola);

        restEscolaMockMvc.perform(put("/api/escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaDTO)))
            .andExpect(status().isOk());

        // Validate the Escola in the database
        List<Escola> escolaList = escolaRepository.findAll();
        assertThat(escolaList).hasSize(databaseSizeBeforeUpdate);
        Escola testEscola = escolaList.get(escolaList.size() - 1);
        assertThat(testEscola.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEscola.getTipoEscola()).isEqualTo(UPDATED_TIPO_ESCOLA);

        // Validate the Escola in Elasticsearch
        Escola escolaEs = escolaSearchRepository.findOne(testEscola.getId());
        assertThat(escolaEs).isEqualToIgnoringGivenFields(testEscola);
    }

    @Test
    public void updateNonExistingEscola() throws Exception {
        int databaseSizeBeforeUpdate = escolaRepository.findAll().size();

        // Create the Escola
        EscolaDTO escolaDTO = escolaMapper.toDto(escola);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEscolaMockMvc.perform(put("/api/escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaDTO)))
            .andExpect(status().isCreated());

        // Validate the Escola in the database
        List<Escola> escolaList = escolaRepository.findAll();
        assertThat(escolaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEscola() throws Exception {
        // Initialize the database
        escolaRepository.save(escola);
        escolaSearchRepository.save(escola);
        int databaseSizeBeforeDelete = escolaRepository.findAll().size();

        // Get the escola
        restEscolaMockMvc.perform(delete("/api/escolas/{id}", escola.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean escolaExistsInEs = escolaSearchRepository.exists(escola.getId());
        assertThat(escolaExistsInEs).isFalse();

        // Validate the database is empty
        List<Escola> escolaList = escolaRepository.findAll();
        assertThat(escolaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchEscola() throws Exception {
        // Initialize the database
        escolaRepository.save(escola);
        escolaSearchRepository.save(escola);

        // Search the escola
        restEscolaMockMvc.perform(get("/api/_search/escolas?query=id:" + escola.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escola.getId())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipoEscola").value(hasItem(DEFAULT_TIPO_ESCOLA.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Escola.class);
        Escola escola1 = new Escola();
        escola1.setId("id1");
        Escola escola2 = new Escola();
        escola2.setId(escola1.getId());
        assertThat(escola1).isEqualTo(escola2);
        escola2.setId("id2");
        assertThat(escola1).isNotEqualTo(escola2);
        escola1.setId(null);
        assertThat(escola1).isNotEqualTo(escola2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscolaDTO.class);
        EscolaDTO escolaDTO1 = new EscolaDTO();
        escolaDTO1.setId("id1");
        EscolaDTO escolaDTO2 = new EscolaDTO();
        assertThat(escolaDTO1).isNotEqualTo(escolaDTO2);
        escolaDTO2.setId(escolaDTO1.getId());
        assertThat(escolaDTO1).isEqualTo(escolaDTO2);
        escolaDTO2.setId("id2");
        assertThat(escolaDTO1).isNotEqualTo(escolaDTO2);
        escolaDTO1.setId(null);
        assertThat(escolaDTO1).isNotEqualTo(escolaDTO2);
    }
}
