package com.kayani.brighthr.login.model;

//public abstract class RestApiRepository {

//    protected void handleResponseError(Response response) {
//        if (!response.isSuccessful()) {
//            ResponseErrorWrapper errorWrapper;
//            try {
//                errorWrapper = new Gson().fromJson(response.errorBody().string(), ResponseErrorWrapper.class);
//                ResponseErrorEntity error = errorWrapper.getError();
//                throw new RestApiErrorException(error.getMessage(), error.getStatus());
//            } catch (IOException | NullPointerException e) {
//                throw new RestApiErrorException(response.message(), response.code());
//            }
//        }
//    }
//}
