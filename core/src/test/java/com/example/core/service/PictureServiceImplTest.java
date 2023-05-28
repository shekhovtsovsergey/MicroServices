package com.example.core.service;

import com.example.core.converter.ProductConverter;
import com.example.core.dao.PictureDao;
import com.example.core.dao.ProductDao;
import com.example.core.dto.PictureDto;
import com.example.core.integration.PictureServiceIntagration;
import com.example.core.model.Picture;
import com.example.core.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(
        classes = {PictureServiceImpl.class},
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Сервис картинок должен")
public class PictureServiceImplTest {

    @Autowired
    PictureServiceImpl pictureService;
    @MockBean
    ProductDao productDao;
    @MockBean
    PictureDao pictureDao;
    @MockBean
    CategoryServiceImpl categoryService;
    @MockBean
    ProductConverter productConverter;
    @MockBean
    PictureServiceIntagration pictureServiceIntagration;


    @Test
    @DisplayName("метод getPictureDataById должен возвращать Optional с нужной картинкой")
    void shouldReturnOptionalWithCorrectPicture() {
        long id = 1L;
        Picture picture = new Picture();
        picture.setId(id);
        picture.setContentType("image/jpeg");
        picture.setStorageFileName("test.jpg");
        Product product = new Product();
        product.setId(id);
        product.getPictures().add(picture);
        PictureDto pictureDto = new PictureDto();
        pictureDto.setContentType(picture.getContentType());
        pictureDto.setData(new byte[]{});
        pictureDto.setPath(Path.of(picture.getStorageFileName()));
        PictureDao pictureDaoMock = mock(PictureDao.class);
        when(pictureDaoMock.findPictureByProductId(id)).thenReturn(Optional.of(picture));
        PictureServiceIntagration pictureServiceIntagrationMock = mock(PictureServiceIntagration.class);
        when(pictureServiceIntagrationMock.getPicture(picture.getStorageFileName())).thenReturn(pictureDto);
        PictureServiceImpl pictureService = new PictureServiceImpl(pictureServiceIntagrationMock, pictureDaoMock);

        Optional<PictureDto> result = pictureService.getPictureDataById(id);

        assertTrue(result.isPresent());
        assertEquals(pictureDto, result.get());
    }

    @Test
    @DisplayName("метод createPicture должен сохранять картинку в базу и возвращать соответствующую модель")
    void shouldSavePictureAndReturnProperModel() {

        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        PictureDao pictureDaoMock = mock(PictureDao.class);
        PictureServiceIntagration pictureServiceIntagrationMock = mock(PictureServiceIntagration.class);
        String fileName = "test.jpg";
        String contentType = "image/jpeg";
        byte[] fileData = new byte[]{};
        PictureDto pictureDto = new PictureDto();
        pictureDto.setContentType(contentType);
        pictureDto.setData(fileData);
        pictureDto.setPath(Path.of(fileName));
        when(pictureServiceIntagrationMock.createPicture(any())).thenReturn(fileName);
        PictureServiceImpl pictureService = new PictureServiceImpl(pictureServiceIntagrationMock, pictureDaoMock);

        Picture result = pictureService.createPicture(mock(MultipartFile.class), product);

        verify(pictureDaoMock, times(1)).save(result);
        verify(pictureServiceIntagrationMock, times(1)).createPicture(any());
        assertEquals(product, result.getProduct());
        assertEquals(fileName, result.getStorageFileName());
        assertEquals(contentType, result.getContentType());
    }
}
