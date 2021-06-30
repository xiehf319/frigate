package cn.cici.frigate.component.verify;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class VerifyImage {

    String srcImage;
    String cutImage;
    Integer xPosition;
    Integer yPosition;
    Integer srcImageWidth;
    Integer srcImageHeight;
}
