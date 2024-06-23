package br.com.restLivro.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import br.com.restLivro.dataDTO.PessoaVO;
import br.com.restLivro.model.pessoa;
import br.com.restLivro.mapeador.DozerMapper;
import br.com.restLivro.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class DozerConverterTest {
    
    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToVOTest() {
        PessoaVO output = DozerMapper.parseObject(inputObject.mockEntity(), PessoaVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("First Name Test0", output.getNome());
        assertEquals("Last Name Test0", output.getSegundoNome());
        assertEquals("Addres Test0", output.getEndereco());
        assertEquals("Male", output.getGenero());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<PessoaVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), PessoaVO.class);
        PessoaVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("First Name Test0", outputZero.getNome());
        assertEquals("Last Name Test0", outputZero.getSegundoNome());
        assertEquals("Addres Test0", outputZero.getEndereco());
        assertEquals("Male", outputZero.getGenero());

        PessoaVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("First Name Test7", outputSeven.getNome());
        assertEquals("Last Name Test7", outputSeven.getSegundoNome());
        assertEquals("Addres Test7", outputSeven.getEndereco());
        assertEquals("Female", outputSeven.getGenero());

        PessoaVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("First Name Test12", outputTwelve.getNome());
        assertEquals("Last Name Test12", outputTwelve.getSegundoNome());
        assertEquals("Addres Test12", outputTwelve.getEndereco());
        assertEquals("Male", outputTwelve.getGenero());
    }

    @Test
    public void parseVOToEntityTest() {
        pessoa output = DozerMapper.parseObject(inputObject.mockVO(), pessoa.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getNome());
        assertEquals("Last Name Test0", output.getSegundoNome());
        assertEquals("Addres Test0", output.getEndereco());
        assertEquals("Male", output.getGenero());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<pessoa> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), pessoa.class);
        pessoa outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getNome());
        assertEquals("Last Name Test0", outputZero.getSegundoNome());
        assertEquals("Addres Test0", outputZero.getEndereco());
        assertEquals("Male", outputZero.getGenero());

        pessoa outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getNome());
        assertEquals("Last Name Test7", outputSeven.getSegundoNome());
        assertEquals("Addres Test7", outputSeven.getEndereco());
        assertEquals("Female", outputSeven.getGenero());

        pessoa outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getNome());
        assertEquals("Last Name Test12", outputTwelve.getSegundoNome());
        assertEquals("Addres Test12", outputTwelve.getEndereco());
        assertEquals("Male", outputTwelve.getGenero());
    }
}
