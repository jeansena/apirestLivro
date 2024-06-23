package br.com.restLivro.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;


import br.com.restLivro.dataDTO.PessoaVO;
import br.com.restLivro.model.pessoa;

public class MockPerson {


    public pessoa mockEntity() {
        return mockEntity(0);
    }
    
    public PessoaVO mockVO() {
        return mockVO(0);
    }
    
    public List<pessoa> mockEntityList() {
        List<pessoa> persons = new ArrayList<pessoa>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<PessoaVO> mockVOList() {
        List<PessoaVO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockVO(i));
        }
        return persons;
    }
    
    public pessoa mockEntity(Integer number) {
        pessoa person = new pessoa();
        person.setEndereco("Addres Test" + number);
        person.setNome("First Name Test" + number);
        person.setGenero(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setSegundoNome("Last Name Test" + number);
        return person;
    }

    public PessoaVO mockVO(Integer number) {
        PessoaVO person = new PessoaVO();
        person.setEndereco("Addres Test" + number);
        person.setNome("First Name Test" + number);
        person.setGenero(((number % 2)==0) ? "Male" : "Female");
        person.setKey(number.longValue());
        person.setSegundoNome("Last Name Test" + number);
        return person;
    }

}
