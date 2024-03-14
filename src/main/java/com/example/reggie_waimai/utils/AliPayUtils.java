package com.example.reggie_waimai.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;

import javax.servlet.http.HttpServletRequest;

public class AliPayUtils {
    public void pay1(HttpServletRequest request1) throws AlipayApiException {
          String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC+3pMJE5QXYXYkG9NDRXEurj4lPyh5TK2CwComypKzS5pV501BGtsn9Lz3TJMtt7dNpwxzozEcZkAUG4aAuYgPvjCEcwZOWUJQPxbTTLiAAaso3qMr9fv4BN9jFbb/XlxQeG7QkiXvo/jhy/wRm65YyBhNNXWWFzaRInOxQd1CceocLfB8kyEDK128oPiKrPu0hoRNSyJVFKd6ADYgkmEE2DQz35QaQHEOmchmMIkte+Gsxi3EhlNNsSDNDEN5uC9TKBCBu1edgVYnfRrscZTT9iQ4QioNE5MBvducANXHbtE1PB3tXOKw96Ak2ZgTOVP0+3hpui2KnZMNpqolep7DAgMBAAECggEBAKGwr2J7AXMlDw3bvIY6Z30iAmdPL5xCRqKC47Jk3Q2iOCYZgaprc8hPXV0ps2yBO3k+0B+N2WazgAkIoFKf2RFtDnDFeEwa9UBBkbaCQbG+uB4xLI1rHn6msg6gMJv4db99pnJtvFFb2NR/FxRbi8COEXgml2wEUid0xgkdQLCtkpbM/qsDnnC4imNY6Xt+qxwbcKBezKxkx0wmS3pCO/W9oviATC+Jy55pLBTv2iTwP0DX8Mr+JOMoZc3vpstzBmO2zTxv9b5KWpHuRwILiJrtVZEy8nIpe7e1U3zuWUkALxHi75U5Qf0/SoDl/Zmxss6+39JeQyBeD5TOihXU7DECgYEA+EkIZmc+y5TIupZXVujKfv6yLrIS95zplwz9889tv0Bp3yGksWzx18Q8EHvhSy/52cPsw84DtbsqZ3gd6BHvrFufsWSn0r9e/f+r/FN/HBCJ3j9Lus7haS/w3/HuCnZVi2m2t0DGmNLmuA7dAIWn2dQoKZ1iXbrKuFB6/3/tPe0CgYEAxMzU0dKyjPKNyHQ6WwQOAQu2rBycKKOPHht6GC/vZnDXrw+msw3azJCulf7R8aLAB/W6K7CqeX0uGmKQZcZ8uVjXGO05zLeMQfvljTnTl7rBGzv1tSbPBl98fr98+l8jLU9FGM0G6gJb3bXCdkMe0u1UdmuhCeP6XNxU+mO/OW8CgYEAsQ7j/qMCFQw1WVp9Tm0UexwG1WYIQKyVqDKLp6L1EL5OweCsIhsfHE/ExbySHZxJARLHdZsk6iRfSQpPyX+A+9kbONYfGBuBEoGRlI+2xbzFlMhuqPl/phOaIxnUN4HL32+z7Vs0RSehgQCYehbWbHDvcz3ZOB5NEsPR8wK3nMECgYEAm2ucg1yfj/qaiH1p/Kk2GhNTH5e0p8+L3l4azXFF4qQpYeK9ZtkBO97jUigdS3SZrW+dqJVr/Ggk+cdvfEEGDSahMNlgdVFbnly+DAtoFILzsHto77iHdOQCIOM/Y0exMz5QNmbtF+/m9zBtNBKMDE5MDv2u/22hMqb7IYeW5FcCgYAcISyKPvg1Gk5lOw7wZqiCotF5XdRxrJZ/3htlTi73Vy2aDismjxLW+pO9nkgCyJwUtAqsTVTC2Kzqmb/Lx+RqUGoMV49ZGFcUceFsaD9wAmWbph3CRt0fdeLiN8nd/69jFAi70o3NqMnZUPY6xejrXQGDw6J9F69D3E3qheQk/Q==";
          String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmbI0miKQ2AtxlUOjEZpnoJOepHM/aMdFT/91t7Xs8QK285Zj1NdMzyaTAkjlYA1ZV8htPO6rdGysgaEvfzl+q+YDzjuKiNRyDkcH4oq1xAcpQAw/S9W0Vrx1Rwjn6G/uUY6k35xpiYYhh/LlxBOWZDwx9kbuZCi2XFrQer4dV37sri3OWPfQRYZPBCApnqouKK0+HJGu+EIU9EW4tUyoZ9NxDzr7wKoSgx+3dZJaNSSknfPReiZoRCJGQa8E3LsfhHQo4YgKcZFbO3uxUzbTz1vt7FizhejT4v0VfnSn+EfpjrR8sAUPbQ1g9Wv4xuBCr/+st0y5vvQKk1TkagC9nQIDAQAB";
          AlipayConfig alipayConfig = new AlipayConfig();
          alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
          alipayConfig.setAppId("9021000135613476");
          alipayConfig.setPrivateKey(privateKey);
          alipayConfig.setFormat("json");
          alipayConfig.setAlipayPublicKey(alipayPublicKey);
          alipayConfig.setCharset("UTF-8");
          alipayConfig.setSignType("RSA2");
          AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
          AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
          AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
          //设置订单编号
          model.setOutTradeNo("2088721031536631");
          model.setTotalAmount("520.00");
          //设置支付的名称
          model.setSubject("大乐透");
          model.setProductCode("QUICK_WAP_WAY");
          model.setSellerId("2088721031536631");
          request.setBizModel(model);
          AlipayTradeWapPayResponse response = alipayClient.pageExecute(request, "POST");
          // 如果需要返回GET请求，请使用
          // AlipayTradeWapPayResponse response = alipayClient.pageExecute(request, "GET");
          String pageRedirectionData = response.getBody();
          System.out.println(pageRedirectionData);
          if (response.isSuccess()) {
              System.out.println("调用成功");
          } else {
              System.out.println("调用失败");
              // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
              // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
              // System.out.println(diagnosisUrl);
          }
      }
}
