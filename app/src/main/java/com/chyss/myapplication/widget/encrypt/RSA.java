package com.chyss.myapplication.widget.encrypt;

/**
 * 用Base64 编码过的 ：Base64.encodeToString(privateKey.getEncoded(), Base64.NO_WRAP)
 * 使用前先解码 : Base64.decode(RSA.PUBLIC_KEY, Base64.NO_WRAP)
 *
 * @author chyss
 */
public abstract class RSA
{
    public static final String PRIVATE_KEY =
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCkhh68RWUZV985swjH8ETcMx0XYEWl7YAJpNKlVAiiJc82ZC7cI2lNXZ88+EGC0gN8YikNb4ICy952tKTccBfHGp4rsfpGeoAno7pgpvXLPXTIBIj/emD6O2lCKN1bqSporfnhqkP6ARf6/D2JGLZn+GF522xVwhiDoy/qJkFaKmMfS9wvbeN7j4y0zEGlNw0GXTZypdMUrRLm7D1PyWgb/qlGDxBcInvOcO9iSTiIDUg+teDb1MS1D8FQ0EZN+LjT+CupP8FbdsUxDYBqy3+YiJi8rT2lwRNMiVgyrZamuUgCXDKMCvUBeVesTlwYnPHCecRPZxso+F0ozqXN0R/jAgMBAAECggEAUJYVVfjTxW8iOxvz0CNjR0yOElYop1bG4XJnb23S8MF+VtEJlvCNcsTJ685aIF8IMBPQsfENiJsdc52WJxeMvgzOru51fbIhNclf0FnXiV6n5hU6mZDzEX6qVyq3Cl+5Ex8sh5mjxEBm45xL8+wz5IdoD40n6nWxFkGHhbSJTEUvwPKA8mocYwuiejo46zgeEqdk4m70j9Swo0D032uBQl1g8Q34sp7B5DArnXg7OvZZnYJ+5kp4Uovh0FmydvBBhQgN/oEARsRzPWYhsf1uJ1P0WBhWovY2OX/0V/8zfku+H6NJI6WTzFMuZy2FzEjgmuFCHn/pzLz6kQ8DW3Q1sQKBgQDR0BRbatFbPTnoSKEY5nkSU0CZy3UJj42fQYT26v5w/EZ8r5KeIuYyrihUS3eWq8xyHlWimAcaisULuwzL2ygbO7OQrTGjqcsKI4hKuDM4Mr88ClQtq+vXEbBrKU2uD12IDytinTyeXsx8kFvbsmSGRvYhqerrWWpmh0Y4myOkGwKBgQDIvc09PuvDgtyxHJD06cQaBMUyDg8mPrwpUCvy/3g4lx8YSdc5w9mwUiI/mtHJQzAPjejK/5Vv8NsAxRUHyAUsjqlmKa/pPMkylrdi+k2yeDctmNClqTuI7x/7n3sC1yWtV3aBjHyRpmRDYJA0OaroSl3NgU2UWCARXMDccW9f2QKBgQCYFMust/CoweiHhp2+/tAjiFT7JY7gLz3oggVvNUbZfwNYvar5FTLm6XYyI8G7ZSv8TCCmiqwbgRrzNdJAscqQcuXlqKSijSCvaLIR9xKpWZ9kVdhOgp+gdSH7iyTTVi/lv5kjEfYulWiDod/zj4tuCWG/ZPrzPQzy5Hrx7QIYoQKBgAgQoBNK2NwFAOrYs/YzAh7tfCKzr+YgdRzPzMQ5gVH8uFIYfV4jwTY1Roz5ol4Mpfyp/eVR+WYC7jmMsXibj6GnpaSmHhwnxgmTRpDib4axD750OkW9k9e/i7BnSuDlVe0t62PvCf4sCjEuyvxeKMCmZDhiboR8OeWYrWLt58SZAoGBAKSTo+80aFjEHjIxhOyqDS+yfBEMqYQcNdGMs4/sxaKdNJwCNkGvn/2laG2wKj/rZk3248/w1NMKQCiGH1hH/7dDrEl4Zx9R0bUunXhr/CmE/06XbSIWofGw3Pi910Skbfk35su4wOJRVuC980x5RSchpo+3oEJeOzaM8lF0Sr4h";

    public static final String PUBLIC_KEY =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApIYevEVlGVffObMIx/BE3DMdF2BFpe2ACaTSpVQIoiXPNmQu3CNpTV2fPPhBgtIDfGIpDW+CAsvedrSk3HAXxxqeK7H6RnqAJ6O6YKb1yz10yASI/3pg+jtpQijdW6kqaK354apD+gEX+vw9iRi2Z/hhedtsVcIYg6Mv6iZBWipjH0vcL23je4+MtMxBpTcNBl02cqXTFK0S5uw9T8loG/6pRg8QXCJ7znDvYkk4iA1IPrXg29TEtQ/BUNBGTfi40/grqT/BW3bFMQ2Aast/mIiYvK09pcETTIlYMq2WprlIAlwyjAr1AXlXrE5cGJzxwnnET2cbKPhdKM6lzdEf4wIDAQAB";
}
