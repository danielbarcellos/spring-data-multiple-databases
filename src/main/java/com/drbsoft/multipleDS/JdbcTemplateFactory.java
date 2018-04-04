package com.drbsoft.multipleDS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.drbsoft.multipleDS.DBConfig.MultipleJdbcTemplate;

/**
 * Este é um factory method que determina a criação e aquisição de um {@link JdbcTemplate} conforme parametro de base
 * específico informado.
 * 
 * @author Daniel Barcellos (danielbarcellos.github.io)
 *
 */
@Component
public class JdbcTemplateFactory {
	
	private JdbcTemplate defaultJdbcTemplate;
	
	@Autowired
	private MultipleJdbcTemplate multiplesJdbcTemplate;
	
	@Autowired
	public JdbcTemplateFactory(JdbcTemplate jdbcTemplate) {
		this.defaultJdbcTemplate = jdbcTemplate;
	}

	/**
	 * Retorna a instancia de {@link JdbcTemplate} padrão da aplicação.
	 * 
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate() {
		return this.defaultJdbcTemplate;
	}
	
	/**
	 * Retorna a instancia de {@link JdbcTemplate} conforme a base a ser utilizada. 
	 * Este metodo é utilizado em multiplas base de dados.
	 * 
	 * @param database - descritor da base de dados.
	 * @return - instancia de {@link JdbcTemplate} 
	 */
	public JdbcTemplate getJdbcTemplate(String database) {
		if(this.multiplesJdbcTemplate.getMultiplesJdbcTemplate().containsKey(database)){
			return this.multiplesJdbcTemplate.getMultiplesJdbcTemplate().get(database);
		}
		
		final String message = "falha ao instanciar jdbc template correspondente (datasoure " + database + " nao localizado).";
		throw new IllegalArgumentException(message);
	}
	
	private void assertNotNull(String message, Object object) {
		if (object == null) {
            throw new IllegalArgumentException(message);
        }
	}
}