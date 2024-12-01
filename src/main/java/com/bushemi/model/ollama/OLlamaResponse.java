package com.bushemi.model.ollama;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OLlamaResponse {
//https://github.com/ollama/ollama/blob/main/docs/api.md
    /**
     * The final response in the stream also includes additional data about the generation:
     *
     * total_duration: time spent generating the response
     * load_duration: time spent in nanoseconds loading the model
     * prompt_eval_count: number of tokens in the prompt
     * prompt_eval_duration: time spent in nanoseconds evaluating the prompt
     * eval_count: number of tokens in the response
     * eval_duration: time in nanoseconds spent generating the response
     * context: an encoding of the conversation used in this response, this can be sent in the next request to keep a conversational memory
     * response: empty if the response was streamed, if not streamed, this will contain the full response
     */
    private final String model;
    private final String created_at;
    private final String response;
    private final boolean done;
    private final String done_reason;
    private final List<Long> context;
    private final Long total_duration;
    private final Long load_duration;
    private final Integer prompt_eval_count;
    private final Long prompt_eval_duration;
    private final Integer eval_count;
    private final Long eval_duration;


}

//{"model":"llama3","created_at":"2024-06-05T09:44:35.524792934Z",
// "response":"The color of the sky can appear different depending on the time of day, atmospheric conditions, and even the observer's location. But in general, the sky appears blue because of a phenomenon called scattering.\n\nScattering occurs when light from the sun interacts with tiny molecules of gases like nitrogen (N2) and oxygen (O2) in the Earth's atmosphere. These molecules are much smaller than the wavelength of visible light, so they scatter shorter wavelengths more effectively. This is known as Rayleigh scattering, named after the British physicist Lord Rayleigh, who first described the phenomenon in the late 19th century.\n\nThe shorter wavelengths of light, like blue and violet, are scattered more than the longer wavelengths, like red and orange. This is because the smaller molecules in the atmosphere are better at deflecting the shorter wavelengths. As a result, our eyes perceive the scattered blue and violet light as making up the majority of the sky's color.\n\nHere's a rough breakdown of what happens:\n\n1. Sunlight enters the Earth's atmosphere, containing all the colors of the visible spectrum.\n2. The shorter wavelengths (blue and violet) are scattered by the tiny molecules in the air, while the longer wavelengths (red and orange) continue to travel in a more direct path to our eyes.\n3. The scattered blue and violet light reaches our eyes from all directions, making the sky appear blue.\n4. As the sun is higher in the sky during the day, the scattering effect becomes even more pronounced, making the sky appear an even deeper shade of blue.\n\nIt's worth noting that the color of the sky can be affected by various factors, such as:\n\n* Atmospheric conditions: Dust, pollution, and water vapor can scatter light in different ways, changing the apparent color of the sky.\n* Time of day: The angle of the sun and the amount of scattering that occurs can change the perceived color of the sky throughout the day.\n* Observer's location: The color of the sky can appear different depending on the observer's altitude, atmospheric conditions, and surrounding environment.\n\nSo, to summarize, the blue color of the sky is primarily due to the scattering of sunlight by tiny molecules in the Earth's atmosphere!","done":true,"done_reason":"stop","context":[128006,882,128007,271,10445,374,279,13180,6437,30,128009,128006,78191,128007,271,791,1933,315,279,13180,649,5101,2204,11911,389,279,892,315,1938,11,45475,4787,11,323,1524,279,22842,596,3813,13,2030,304,4689,11,279,13180,8111,6437,1606,315,264,25885,2663,72916,382,3407,31436,13980,994,3177,505,279,7160,84261,449,13987,35715,315,45612,1093,47503,320,45,17,8,323,24463,320,46,17,8,304,279,9420,596,16975,13,4314,35715,527,1790,9333,1109,279,46406,315,9621,3177,11,779,814,45577,24210,93959,810,13750,13,1115,374,3967,439,13558,64069,72916,11,7086,1306,279,8013,83323,10425,13558,64069,11,889,1176,7633,279,25885,304,279,3389,220,777,339,9478,382,791,24210,93959,315,3177,11,1093,6437,323,80836,11,527,38067,810,1109,279,5129,93959,11,1093,2579,323,19087,13,1115,374,1606,279,9333,35715,304,279,16975,527,2731,520,84040,287,279,24210,93959,13,1666,264,1121,11,1057,6548,45493,279,38067,6437,323,80836,3177,439,3339,709,279,8857,315,279,13180,596,1933,382,8586,596,264,11413,31085,315,1148,8741,1473,16,13,8219,4238,29933,279,9420,596,16975,11,8649,682,279,8146,315,279,9621,20326,627,17,13,578,24210,93959,320,12481,323,80836,8,527,38067,555,279,13987,35715,304,279,3805,11,1418,279,5129,93959,320,1171,323,19087,8,3136,311,5944,304,264,810,2167,1853,311,1057,6548,627,18,13,578,38067,6437,323,80836,3177,25501,1057,6548,505,682,18445,11,3339,279,13180,5101,6437,627,19,13,1666,279,7160,374,5190,304,279,13180,2391,279,1938,11,279,72916,2515,9221,1524,810,38617,11,3339,279,13180,5101,459,1524,19662,28601,315,6437,382,2181,596,5922,27401,430,279,1933,315,279,13180,649,387,11754,555,5370,9547,11,1778,439,1473,9,87597,4787,25,33093,11,25793,11,323,3090,38752,649,45577,3177,304,2204,5627,11,10223,279,10186,1933,315,279,13180,627,9,4212,315,1938,25,578,9392,315,279,7160,323,279,3392,315,72916,430,13980,649,2349,279,26617,1933,315,279,13180,6957,279,1938,627,9,35141,596,3813,25,578,1933,315,279,13180,649,5101,2204,11911,389,279,22842,596,36958,11,45475,4787,11,323,14932,4676,382,4516,11,311,63179,11,279,6437,1933,315,279,13180,374,15871,4245,311,279,72916,315,40120,555,13987,35715,304,279,9420,596,16975,0,128009],"total_duration":407157440166,"load_duration":3628299827,"prompt_eval_count":15,"prompt_eval_duration":3593914000,"eval_count":442,"eval_duration":399932374000}