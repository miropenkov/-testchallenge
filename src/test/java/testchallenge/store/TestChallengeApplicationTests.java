package testchallenge.store;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Ignore
public class TestChallengeApplicationTests {
    private static TestRequester REQUESTER = new TestRequester();

    private final String TEST_ROOT_URL = "http://localhost:8080/rest";

    @Test
    public void contextLoads() {
        System.out.println("contextLoads TestChallengeApplicationTests");
    }

    @Test
    public void getCategories() throws JSONException{
        Object result = REQUESTER.runGet(TEST_ROOT_URL + "/categories");
        Assert.isTrue(Objects.nonNull(result),"The response result is compromised");
        System.out.println( ( (JSONArray)result).toString());
    }


    @Test
    public void createProductOrder() throws JSONException{
        Object result = null;
        Long productId = 4L;
        Long quantity = 10L;
        try {
            result = REQUESTER.runPost(TEST_ROOT_URL + "/products/" + productId + "/order/" + quantity, new StringEntity(""));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Assert.isTrue(Objects.nonNull(result),"The response result is compromised");

        Long respProductId = 0L;
        try {
            respProductId = ((JSONObject)result).getLong("Id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Assert.isTrue(!productId.equals(respProductId) , "The response REST result is wrong");
    }

    @Test
    public void createProduct() throws JSONException {
        Object result = null;
        JSONObject requestCreateProduct = new JSONObject();
        requestCreateProduct.put("name","dev-kit");
        requestCreateProduct.put("category","Software");
        requestCreateProduct.put("description","Development Kit");
        requestCreateProduct.put("quantity","10");
        try {
            result = REQUESTER.runPost(TEST_ROOT_URL + "/product", new StringEntity(requestCreateProduct.toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Assert.isTrue(Objects.nonNull(result),"The response result is compromised");
        Long respProductId = null;
        try {
            respProductId = ((JSONObject)result).getLong("Id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
         Assert.isTrue( Objects.nonNull(respProductId) , "The response REST result is wrong");
    }

    @Test
    public void updateProduct() throws JSONException {
        Long productId = 2L;
        Object result = null;
        JSONObject requestUpdateProduct = new JSONObject();
        requestUpdateProduct.put("name","Submarin");
        requestUpdateProduct.put("category","Marine boats");
        requestUpdateProduct.put("description","Submarin boats 200m length");
        requestUpdateProduct.put("quantity","2");
        Long quantity = 10L;
        try {
            result = REQUESTER.runPut(TEST_ROOT_URL + "/product/" + productId + "/product", new StringEntity(requestUpdateProduct.toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Assert.isTrue(Objects.nonNull(result),"The response result is compromised");
        Long respProductId = 0L;
        try {
            respProductId = ((JSONObject)result).getLong("Id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Assert.isTrue(productId.equals(respProductId) , "The response REST result is wrong");
    }

    @Test
    public void deleteProduct() throws JSONException {
        Long productId = 1L;
        Object result = REQUESTER.runDelete(TEST_ROOT_URL + "/product/" + productId + "/product");
        Assert.isTrue(Objects.nonNull(result),"The response result is compromised");
    }

    @Test
    public void getProduct()  throws JSONException {
        Long productId = 1L;
        Object result = REQUESTER.runGet(TEST_ROOT_URL + "/product/" + productId + "/product");
        Assert.isTrue(Objects.nonNull(result),"The response result is compromised");
        Long respProductId = 0L;
        try {
            respProductId = ((JSONObject)result).getLong("Id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Assert.isTrue(!productId.equals(respProductId) , "The response REST result is wrong");
    }

    @Test
    public void getProducts() {
        Object result = null;
        String direction = "DESC";
        String orderBy = "name";
        int page = 2;
        int pageSize = 3;
        result = REQUESTER.runGet(TEST_ROOT_URL + "/product/list?direction=" + direction + "&orderBy=" + orderBy + "&page=" + page + "&pageSize=" + pageSize);

        Assert.isTrue(Objects.nonNull(result),"The response result is compromised");
    }
}
