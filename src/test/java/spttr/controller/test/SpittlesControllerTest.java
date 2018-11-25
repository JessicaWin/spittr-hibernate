package spttr.controller.test;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import spittr.dao.SpitterRepository;
import spittr.dao.SpittleRepository;
import spittr.dto.Spittle;
import spittr.web.SpitterController;
import spittr.web.SpittleController;

public class SpittlesControllerTest {

	@Test
	public void shouldShowRecentSpittles() throws Exception {
		List<Spittle> expectedSpittles = createSpittleList(20);
		SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
		SpittleController controller = new SpittleController(mockRepository);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/spittles"))
				.andExpect(MockMvcResultMatchers.view().name("spittles"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("spittleList")).andExpect(MockMvcResultMatchers
						.model().attribute("spittleList", Matchers.hasItems(expectedSpittles.toArray())));
	}

	@Test
	public void shouldShowPagedSpittles() throws Exception {
		List<Spittle> expectedSpittles = createSpittleList(50);
		SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
		SpittleController controller = new SpittleController(mockRepository);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/spittles?max=238900&count=50"))
				.andExpect(MockMvcResultMatchers.view().name("spittles"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("spittleList")).andExpect(MockMvcResultMatchers
						.model().attribute("spittleList", Matchers.hasItems(expectedSpittles.toArray())));
	}

	@Test
	public void shouldShowRegistration() throws Exception {
		SpitterRepository mockRepository = Mockito.mock(SpitterRepository.class);
		SpitterController controller = new SpitterController(mockRepository);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/spitter/register"))
				.andExpect(MockMvcResultMatchers.view().name("registerForm"));
	}

	public List<Spittle> createSpittleList(int count) {
		List<Spittle> spittleList = new ArrayList<Spittle>();
		for (int i = 0; i < count; i++) {
			// spittleList.add(new Spittle(new Date().getTime(), new Date().getTime(),
			// "Spittle" + i, new Date()));
		}
		return spittleList;
	}
}
