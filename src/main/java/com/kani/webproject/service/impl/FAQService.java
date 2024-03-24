package com.kani.webproject.service.impl;

import com.kani.webproject.dto.FAQDto;
import com.kani.webproject.entity.FAQ;
import com.kani.webproject.entity.Product;
import com.kani.webproject.repository.IFAQRepository;
import com.kani.webproject.repository.IProductRepository;
import com.kani.webproject.service.IFAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQService implements IFAQService {
    private final IFAQRepository faqRepository;
    private final IProductRepository productRepository;

    @Override
    public FAQDto createFaq(Long productId, FAQDto faqDto){
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            FAQ faq = new FAQ();
            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(product.get());
            return faqRepository.save(faq).getFAQDto();
        }
        return null;
    }


}
