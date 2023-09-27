package com.coding404.myweb.product.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Service("productService") //Service어노테이션은 구현체에...
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductMapper productMapper;
	/////////////////////////////
	@Value("${project.upload.path}") //application.properties의 값 가져오기
	private String uploadPath;
	
	
	//폴더 생성 함수
	public String makeFolder() {
		String path = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		File file = new File(uploadPath + "/" + path);
		
		if(file.exists() == false) { //존재하면 true, 존재하지 않으면 false
			file.mkdirs();
		}
		return path; //날짜 폴더명 반환
	}
	
	

	//////////////////////////////////////
	// 하나의 메서드에서 여러 CRUD작업이 일어나는 경우에 한 부분이 에러가 발생하면 그 에러를 처리하고 롤백처리를 대신한다
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int productRegist(ProductVO vo, List<MultipartFile> list) {
		//product 테이블 처리
		int result = productMapper.productRegist(vo);
		
		//업로드 처리
		for (MultipartFile file : list) {

			System.out.println(file.isEmpty());

			// 파일 이름을 받음
			String originname = file.getOriginalFilename();

			// 브라우저 별로 파일의 경로가 다를 수 있기 떄문에 \\기준으로 파일명만 잘라서 다시 저장
			String filename = originname.substring(originname.lastIndexOf("\\") + 1);

			// 파일 사이즈
			long size = file.getSize();

			// 동일한 파일을 재업로드 시 기존 파일을 덮어버리기 때문에 난수이름으로 파일명을 바꿔서 올림
			String uuid = UUID.randomUUID().toString();

			// 날짜별로 폴더 생성
			String filepath = makeFolder();

			// save할 경로
			String savepath = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;

			File saveFile = new File(savepath);
			try {
				file.transferTo(saveFile); // 파일 업로드를 진행
			} catch (Exception e) {
				System.out.println("파일업로드 중 error발생");
				e.printStackTrace();
				return 0; //실패의 의미
			}
			

			// 데이터베이스에 추후 저장
			System.out.println(originname);
			System.out.println("실제파일명: " + filename);
			System.out.println("난수값: " + uuid);
			System.out.println(uploadPath);
			System.out.println("세이브할 경로: " + savepath);
			System.out.println("날짜폴더경로: " + filepath);

			//productUpload 테이블에 파일의 경로 insert
			//prod_id는 insert전에 selectKey를 통해서 얻음
			productMapper.productFileRegist(ProductUploadVO.builder()
															.filename(filename)
															.filepath(filepath)
															.uuid(uuid)
															.prod_writer(vo.getProd_writer())
															.build());
			
		} // end for
		
		return result;
	}



	@Override
	public ArrayList<ProductVO> getList(String writer, Criteria cri) {
		return productMapper.getList(writer, cri);
	}



	@Override
	public int getTotal(String writer, Criteria cri) {
		return productMapper.getTotal(writer, cri);
	}



	@Override
	public ProductVO getDetail(int prod_id) {
		return productMapper.getDetail(prod_id);
	}



	@Override
	public int productUpdate(ProductVO vo) {
		return productMapper.productUpdate(vo);
	}



	@Override
	public void productDelete(int prod_id) {
		productMapper.productDelete(prod_id);
		
	}



	@Override
	public ArrayList<CategoryVO> getCategory() {
		return productMapper.getCategory();
	}



	@Override
	public ArrayList<CategoryVO> getCategoryChild(CategoryVO vo) {
		return productMapper.getCategoryChild(vo);
	}



	//이미지 불러오기
	@Override
	public ArrayList<ProductUploadVO> getAjaxImg(Integer prod_id) {
		return productMapper.getAjaxImg(prod_id);
	}

	

}
