//package book;
//
//import book.login.dao.MemberRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MemberRepoTest {
//
//    @Autowired
//    MemberRepository customerRepository;
//
//    @Test
//    public void testCustomerRepository(){
//        Customer customer = Customer.builder().name("크리스").phone("010-1224-1225").build();
//        customerRepository.save(customer);
//
//        List<Customer> customerList = customerRepository.findAll();
//
//        Customer chris = customerList.get(0);
//        assertThat(chris.getName(), is("크리스"));
//        assertThat(chris.getPhone(), is("010-1224-1225"));
//    }
//
//    @After
//    public void deleteAll() {
//        customerRepository.deleteAll();
//    }
//
//}