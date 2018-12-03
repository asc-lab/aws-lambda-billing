package pl.altkom.asc.lab.lambda.billing

import com.amazonaws.services.s3.event.S3EventNotification
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.assertEquals


class GenerateBillingItemFuncFunctionTest : Spek({

    val function: GenerateBillingItemFunction = DaggerFunctionComponent.builder().functionModule(FunctionTestModule()).build().provideFunction()

    describe("generate-billing-item-func function") {

        it("should return 'generate-billing-item-func'") {
            assertEquals(function.apply(
                    S3EventNotification.parseJson(
                            "{\n" +
                                    "    \"Records\": [\n" +
                                    "        {\n" +
                                    "            \"awsRegion\": \"us-east-2\",\n" +
                                    "            \"eventName\": \"ObjectCreated:Put\",\n" +
                                    "            \"eventSource\": \"aws:s3\",\n" +
                                    "            \"eventTime\": \"2018-10-18T14:08:19.406Z\",\n" +
                                    "            \"eventVersion\": \"2.0\",\n" +
                                    "            \"requestParameters\": {\n" +
                                    "                \"sourceIPAddress\": \"195.167.145.197\"\n" +
                                    "            },\n" +
                                    "            \"responseElements\": {\n" +
                                    "                \"x-amz-id-2\": \"aSQfUTktjIG3Ix4AxYQO3RGMmUGVX9IL6qUAgAoQyyJJNmKmv42bWeuY8jNcISiHHVyK0azzDGA=\",\n" +
                                    "                \"x-amz-request-id\": \"8247250DB3A4CC72\"\n" +
                                    "            },\n" +
                                    "            \"s3\": {\n" +
                                    "                \"configurationId\": \"tf-s3-lambda-20181018122407553500000003\",\n" +
                                    "                \"bucket\": {\n" +
                                    "                    \"name\": \"asc-lab-serverless-input\",\n" +
                                    "                    \"ownerIdentity\": {\n" +
                                    "                        \"principalId\": \"A29BXRD0OXNBDY\"\n" +
                                    "                    },\n" +
                                    "                    \"arn\": \"arn:aws:s3:::asc-lab-serverless-input\"\n" +
                                    "                },\n" +
                                    "                \"object\": {\n" +
                                    "                    \"key\": \"ASC_2018_02_activeList.csv\",\n" +
                                    "                    \"size\": 179,\n" +
                                    "                    \"eTag\": \"5996a86a5feffb6cbffa7ed7af8069fc\",\n" +
                                    "                    \"versionId\": null,\n" +
                                    "                    \"sequencer\": \"005BC893D35E75F02F\",\n" +
                                    "                    \"urlDecodedKey\": \"ASC_2018_02_activeList.csv\"\n" +
                                    "                },\n" +
                                    "                \"s3SchemaVersion\": \"1.0\"\n" +
                                    "            },\n" +
                                    "            \"userIdentity\": {\n" +
                                    "                \"principalId\": \"A29BXRD0OXNBDY\"\n" +
                                    "            }\n" +
                                    "        }\n" +
                                    "    ]\n" +
                                    "}"
                    )
            ), "OK")
        }
    }
})
